package com.backend.api.clova;

import com.backend.api.interview.dto.InterviewDto;
import com.backend.api.interview.dto.InterviewFeedback;
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
import org.json.JSONArray;
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
    public String interviewFeedbackByClova(InterviewDto interviewDto) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(CLOVA_STUDIO_API_URL_TEMPLATE);
            setHeaders(httpPost);

            // 피드백 및 점수 평가 요청 본문 생성
            String feedbackPrompt = createFeedbackPrompt();
            String ratePrompt = createRatePrompt();
            String feedbackRequestBody = createRequestBody(feedbackPrompt, interviewDto.getQuestions(), interviewDto.getAnswers());
            String rateRequestBody = createRequestBody(ratePrompt, interviewDto.getQuestions(), interviewDto.getAnswers());

            // 피드백 요청
            httpPost.setEntity(new StringEntity(feedbackRequestBody, ContentType.APPLICATION_JSON));
            HttpResponse feedbackResponse = httpClient.execute(httpPost);
            logContentType(feedbackResponse);
            String feedbackResponseBody = getResponseBody(feedbackResponse);
            System.out.println("여기가 피드백" + feedbackResponseBody);

            System.out.println("여기 피드백" + feedbackResponseBody);

            // 점수 요청
            httpPost.setEntity(new StringEntity(rateRequestBody, ContentType.APPLICATION_JSON));
            HttpResponse rateResponse = httpClient.execute(httpPost);
            logContentType(rateResponse);
            String rateResponseBody = getResponseBody(rateResponse);

            // 응답에서 점수 파싱
            List<Integer> scores = parseScoresFromResponse(rateResponseBody);

            // 점수 출력
            System.out.println("Scores: " + scores);

            return "성공";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    private String sendRequest(CloseableHttpClient httpClient, String prompt, List<String> questions, List<String> answers) throws IOException {
        HttpPost httpPost = new HttpPost(CLOVA_STUDIO_API_URL_TEMPLATE);
        setHeaders(httpPost);

        String requestBody = createRequestBody(prompt, questions, answers);

        // 요청 본문 로깅
        logger.info("Request body: " + requestBody);

        StringEntity entity = new StringEntity(requestBody, "UTF-8");
        entity.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        httpPost.setEntity(entity);

        HttpResponse response = httpClient.execute(httpPost);

        // 상태 코드 확인
        int statusCode = response.getStatusLine().getStatusCode();
        logger.info("Response status code: " + statusCode);

        // 응답 본문 확인
        String responseBody = getResponseBody(response);
        logger.info("Response body: " + responseBody);

        return responseBody;
    }

    private String getResponseBody(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            return EntityUtils.toString(entity);
        }
        return "";
    }

    private void setHeaders(HttpPost httpPost) {
        httpPost.setHeader("X-NCP-CLOVASTUDIO-API-KEY", API_KEY);
        httpPost.setHeader("X-NCP-APIGW-API-KEY", API_GATEWAY_KEY);
        httpPost.setHeader("Content-Type", "application/json");
    }

    private String createFeedbackPrompt() {
        return "너는 채용담당자야. \n" +
                "면접 질문과 면접 답변을 보고 엄격한 평가를 진행해줘. \n" +
                "\n" +
                "평가방법 : \n" +
                "면접자의 답변 각각에 대한 평가를 진행해. \n" +
                "하나의 평가 결과는 하나의 배열 요소로 저장하여, 출력해.\n" +
                "\n" +
                "출력 예시 : [\"\", ] 와 같은 하나의 배열만 출력";
    }

    private String createRatePrompt() {
        return "너는 채용담당자야. \n" +
                "면접을 본 면접자에 대한 평가 점수를 책정해.\n" +
                "\n" +
                "평가방법 : \n" +
                "\n" +
                "[평가 항목]\n" +
                "1. 적절성\n" +
                "질문에 대한 답변 내용이 적절한가\n" +
                "\n" +
                "2. 구체성\n" +
                "질문에 대한 답변 내용이 구체적인가\n" +
                "\n" +
                "3. 논리성\n" +
                "질문에 대한 답변이 일관되고, 근거와 예시로 주장을 뒷받침 하는가\n" +
                "\n" +
                "4. 유창성\n" +
                "말을 더듬거나, 문장의 끝을 흐리지 않는가\n" +
                "\n" +
                "5. 자신감\n" +
                "내용에서 자신감이 느껴지는 표현\n" +
                "\n" +
                "면접자의 전체적인 답변을 제시된 다섯 가지 평가 항목으로 평가해. 그리고 평가 출력은 각 평가 항목에 대한 하나의 백분율 숫자값으로 표현하여 배열의 요소로 출력해\n" +
                "\n" +
                "출력 예시 : [1,2,3,4,5] 와 같은 하나의 배열만 출력";
    }

    private String createRequestBody(String prompt, List<String> questions, List<String> answers) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String questionsJson = objectMapper.writeValueAsString(questions);
        String answersJson = objectMapper.writeValueAsString(answers);

        // Ensure JSON content is properly escaped and formatted
        // 요청 본문 형식 확인
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

    private List<String> extractFeedbacksFromResponse(String responseBody) {
        List<String> feedbacks = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode contentNode = rootNode.path("result").path("message").path("content");

            if (contentNode.isTextual()) {
                String content = contentNode.asText();
                JsonNode feedbacksNode = objectMapper.readTree(content);

                if (feedbacksNode.isArray()) {
                    for (JsonNode feedbackNode : feedbacksNode) {
                        if (feedbackNode.isTextual()) {
                            feedbacks.add(feedbackNode.asText().trim());
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Error extracting feedback from response", e);
        }
        return feedbacks;
    }


    public InterviewFeedback getInterviewFeedbackAndScore(InterviewDto interviewDto) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(CLOVA_STUDIO_API_URL_TEMPLATE);
            setHeaders(httpPost);

            // 피드백 및 점수 평가 요청 본문 생성
            String feedbackPrompt = createFeedbackPrompt();
            String ratePrompt = createRatePrompt();
            String feedbackRequestBody = createRequestBody(feedbackPrompt, interviewDto.getQuestions(), interviewDto.getAnswers());
            String rateRequestBody = createRequestBody(ratePrompt, interviewDto.getQuestions(), interviewDto.getAnswers());

            // 피드백 요청
            httpPost.setEntity(new StringEntity(feedbackRequestBody, ContentType.APPLICATION_JSON));
            HttpResponse feedbackResponse = httpClient.execute(httpPost);
            logContentType(feedbackResponse);
            String feedbackResponseBody = getResponseBody(feedbackResponse);

            // 점수 요청
            httpPost.setEntity(new StringEntity(rateRequestBody, ContentType.APPLICATION_JSON));
            HttpResponse rateResponse = httpClient.execute(httpPost);
            logContentType(rateResponse);
            String rateResponseBody = getResponseBody(rateResponse);

            // 응답에서 피드백과 점수 파싱
            List<String> feedbacks = extractFeedbacksFromResponse(feedbackResponseBody);
            System.out.println("이게 피드백!! " + feedbackResponseBody);
            List<Integer> scores = parseScoresFromResponse(rateResponseBody);

            return InterviewFeedback.builder()
                    .feedbacks(feedbacks)
                    .scores(scores)
                    .build();
        } catch (Exception e) {
            logger.error("Error getting interview feedback and score", e);
            throw new RuntimeException("Error getting interview feedback and score", e);
        }
    }


    private List<Integer> parseScoresFromResponse(String responseBody) {
        List<Integer> scores = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode contentNode = rootNode.path("result").path("message").path("content");

            if (contentNode.isTextual()) {
                String content = contentNode.asText();
                JsonNode scoresNode = objectMapper.readTree(content);

                if (scoresNode.isArray()) {
                    for (JsonNode scoreNode : scoresNode) {
                        if (scoreNode.isInt()) {
                            scores.add(scoreNode.asInt());
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Error parsing scores from response", e);
        }
        return scores;
    }
}