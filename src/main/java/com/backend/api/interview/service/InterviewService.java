package com.backend.api.interview.service;

import com.backend.api.interview.dto.InterviewRequest;
import com.backend.api.interview.entity.Interview;
import com.backend.api.interview.repository.InterviewRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private static final String CLOVA_API_URL = "https://naveropenapi.apigw.ntruss.com/text-summary/v1/summarize";

    @Value("${naver.clova.clientid}")
    private String CLIENTID;

    @Value("${naver.clova.secretkey}")
    private String SECRETKEY;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public Interview createInterview(InterviewRequest request, String fileName) throws Exception {
        List<String> generatedQuestions = generateQuestions(request, fileName);

        Interview interview = Interview.builder()
                .company(request.getCompanyName())
                .field(request.getField())
                .questions(generatedQuestions)
                .fileName(fileName) // 파일명 저장
                .build();

        return interviewRepository.save(interview);
    }

    public List<String> generateQuestions(InterviewRequest request, String fileName) throws IOException {
        Path filePath = Paths.get("uploads/", fileName); // 파일 경로
        byte[] fileContent = Files.readAllBytes(filePath);
        String summary = summarizeText(fileContent);

        // TO-DO : request로 받은 사전질문으로 질문 생성

        // 자소서 요약내용으로 질문 생성
        List<String> questions = new ArrayList<>();
        questions.add("Generated interview questions based on summary: " + summary);
        return questions;
    }

    public String summarizeText(byte[] fileContent) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(CLOVA_API_URL);
            httpPost.setHeader("X-NCP-APIGW-API-KEY-ID", CLIENTID);
            httpPost.setHeader("X-NCP-APIGW-API-KEY", SECRETKEY);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("file", new ByteArrayInputStream(fileContent), ContentType.APPLICATION_OCTET_STREAM, "document");
            HttpEntity multipart = builder.build();
            httpPost.setEntity(multipart);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                String responseBody = EntityUtils.toString(responseEntity);
                return extractSummaryFromResponse(responseBody);
            }
        }
    }

    private String extractSummaryFromResponse(String responseBody) {
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode summaryNode = rootNode.path("summary");
            return summaryNode.asText().trim();
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse response JSON", e);
        }
    }
}
