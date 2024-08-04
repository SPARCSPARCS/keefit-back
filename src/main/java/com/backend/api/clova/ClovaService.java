package com.backend.api.clova;

import com.backend.api.interview.dto.InterviewRequest;
import com.backend.api.interview.dto.InterviewFeedback;
import com.backend.api.companyInterview.dto.CompanyInterviewDto;
import com.backend.api.jobInterview.dto.JobInterviewDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClovaService {

    private static final String CLOVA_STUDIO_API_URL_TEMPLATE = "https://clovastudio.stream.ntruss.com/testapp/v1/chat-completions/HCX-003";
    private static final Logger logger = LoggerFactory.getLogger(ClovaService.class);

    @Value("${naver.clova.apiKey}")
    private String API_KEY;

    @Value("${naver.clova.apiGatewayKey}")
    private String API_GATEWAY_KEY;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Clova API request body
    private String getResponseBody(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            return EntityUtils.toString(entity);
        }
        return "";
    }

    // Clova API request header
    private void setHeaders(HttpPost httpPost) {
        httpPost.addHeader("X-NCP-CLOVASTUDIO-API-KEY", API_KEY);
        httpPost.addHeader("X-NCP-APIGW-API-KEY", API_GATEWAY_KEY);
        httpPost.addHeader("Content-Type", "application/json");
    }

    // Clova API request body
    private String createRequestBody(String prompt, List<String> questions, List<String> answers) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String questionsJson = objectMapper.writeValueAsString(questions);
        String answersJson = objectMapper.writeValueAsString(answers);

        // Ensure JSON content is properly escaped and formatted
        return "{\n" +
                "  \"messages\" : [ {\n" +
                "    \"role\" : \"system\",\n" +
                "    \"content\" : \"" + prompt + "\"\n" +
                "  }, {\n" +
                "    \"role\" : \"user\",\n" +
                "    \"content\" : \"" + questionsJson.replace("\"", "\\\"") + "\\n\\n\\n" + answersJson.replace("\"", "\\\"") + "\"\n" +
                "  } ],\n" +
                "  \"topP\" : 0.8,\n" +
                "  \"topK\" : 0,\n" +
                "  \"maxTokens\" : 256,\n" +
                "  \"temperature\" : 0.5,\n" +
                "  \"repeatPenalty\" : 5.0,\n" +
                "  \"stopBefore\" : [ ],\n" +
                "  \"includeAiFilters\" : true,\n" +
                "  \"seed\" : 0\n" +
                "}";
    }

    private void logContentType(HttpResponse response) {
        Header contentTypeHeader = response.getFirstHeader("Content-Type");
        if (contentTypeHeader != null) {
            logger.info("Content-Type: " + contentTypeHeader.getValue());
        }
    }
    // Json에서 평가 점수 파싱
    private List<Integer> parseScoresFromResponse(String responseBody) {
        List<Integer> scores = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode contentNode = rootNode.path("result").path("message").path("content");

            // Split the content by new line to get each score
            if (contentNode.isTextual()) {
                String content = contentNode.asText();
                String[] lines = content.split("\n");

                for (String line : lines) {
                    // Extract scores from the format "perform : 2\nknowledge : 1"
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        try {
                            int score = Integer.parseInt(parts[1].trim());
                            scores.add(score);
                        } catch (NumberFormatException e) {
                            logger.warn("Failed to parse score from line: " + line);
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Error parsing scores from response", e);
        }
        return scores;
    }

    // 직무 적합 인터뷰 피드백, 평가 점수 요청 - Clova API
    public List<Integer> getJobInterviewScore(String prompt, List<String> questions, List<String> answers) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(CLOVA_STUDIO_API_URL_TEMPLATE);
            setHeaders(httpPost);

            String rateRequestBody = createRequestBody(prompt, questions, answers);

            httpPost.setEntity(new StringEntity(rateRequestBody, ContentType.APPLICATION_JSON));
            HttpResponse rateResponse = httpClient.execute(httpPost);
            logContentType(rateResponse);
            String rateResponseBody = getResponseBody(rateResponse);
            System.out.println("점수는 :  " + rateResponseBody);

            // 응답에서 피드백, 점수 파싱
            return parseScoresFromResponse(rateResponseBody);
        } catch (Exception e) {
            logger.error("Error getting interview feedback and score", e);
            throw new RuntimeException("Error getting interview feedback and score", e);
        }
    }

}