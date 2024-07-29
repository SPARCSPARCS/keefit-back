package com.backend.api.clova;

import com.backend.api.interview.dto.InterviewRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClovaService {

    private static final String CLOVA_STUDIO_API_URL_TEMPLATE = "https://clovastudio.stream.ntruss.com/testapp/v1/chat-completions/HCX-003";
    private static final Logger logger = LoggerFactory.getLogger(ClovaService.class);

    @Value("${naver.clova.apiKey}")
    private String apiKey;

    @Value("${naver.clova.apiGatewayKey}")
    private String apiGatewayKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Clova API 연결
    public List<String> createInterviewByClova(String companyInfo, String field) {
        String apiUrl = CLOVA_STUDIO_API_URL_TEMPLATE;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(apiUrl);
            setHeaders(httpPost);

            String clovaPrompt = createPrompt(companyInfo, field);
            String requestBody = createRequestBody(clovaPrompt, companyInfo, field);
            httpPost.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));

            HttpResponse response = httpClient.execute(httpPost);
            logContentType(response);

            String responseBody = getResponseBody(response);
            return extractQuestionsFromResponse(responseBody);
        } catch (Exception e) {
            logger.error("An error occurred while creating interview questions: ", e);
            return Collections.emptyList();
        }
    }

    private void setHeaders(HttpPost httpPost) {
        httpPost.addHeader("X-NCP-CLOVASTUDIO-API-KEY", apiKey);
        httpPost.addHeader("X-NCP-APIGW-API-KEY", apiGatewayKey);
        httpPost.addHeader("Content-Type", "application/json");
    }

    private String createPrompt(String companyInfo, String field) {
        return "너는 채용담당자야. 사용자가 채용 공고 링크를 보내면 그에 맞는 면접 질문을 배열 형태로 출력하는 역할이야. 한국어로 답변하고 엄격한 언어로 길게 답변해."
                + "면접 지원자의 희망 직무, 회사 채용 정보는 직접 제공해줄게."
                + "희망 직무는 " + field + ", 지원하는 회사의 채용 정보는 " + companyInfo + "이고, "
                + "직무와 관련된 기술 질문 1개, 채용 공고를 참고하여 질문 1개, 지원자의 인성을 알 수 있는 인성 질문 1개를 생성해주세요."
                + "첫번째 질문은 기술 관련 질문, 두번째 질문은 채용 공고 관련 질문, 세번째 질문은 인성 질문으로 생성해줘";
    }

    private String createRequestBody(String clovaPrompt, String companyInfo, String field) {
        return "{\n" +
                "  \"messages\" : [ {\n" +
                "    \"role\" : \"system\",\n" +
                "    \"content\" : \"" + clovaPrompt + "\"\n" +
                "  }, {\n" +
                "    \"role\" : \"user\",\n" +
                "    \"content\" : \"" + companyInfo + " " + field + "\"\n" +
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

    private String getResponseBody(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            return EntityUtils.toString(entity);
        }
        return "";
    }

    private List<String> extractQuestionsFromResponse(String responseBody) {
        List<String> questions = new ArrayList<>();
        System.out.println("Response content: " + responseBody);
        try {
            // Parse the response JSON
            JsonNode rootNode = objectMapper.readTree(responseBody);

            // Extract the 'result.message.content' node
            JsonNode contentNode = rootNode.path("result").path("message").path("content");

            if (contentNode.isTextual()) {
                // Split the content into questions based on the delimiter
                String content = contentNode.asText();

                // Extract questions by splitting based on the newline character or specific delimiters
                String[] extractedQuestions = content.split("\n\n");

                // Add the extracted questions to the list
                Collections.addAll(questions, extractedQuestions);
            }
        } catch (IOException e) {
            logger.error("Error extracting questions from response", e);
        }

        return questions;
    }
}