package com.backend.api.interview.service;

import com.backend.api.clova.ClovaService;
import com.backend.api.jobInterview.repository.JobInterviewRepository;
import com.backend.api.interview.dto.InterviewRequest;
import com.backend.api.interview.dto.InterviewFeedback;
import com.backend.api.companyInterview.dto.CompanyInterviewDto;
import com.backend.api.jobInterview.dto.JobInterviewDto;
import com.backend.api.interview.entity.Interview;
import com.backend.api.jobInterview.entity.JobInterview;
import com.backend.api.interview.repository.InterviewRepository;
import com.backend.api.member.entity.Member;
import com.backend.api.member.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
@RequiredArgsConstructor
public class InterviewService {

    private final MemberRepository memberRepository;
    private final InterviewRepository interviewRepository;
    private final ClovaService clovaService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RestTemplate restTemplate;

    // 면접 목록 조회
    @Transactional
    public List<Interview> getInterviewList(String memberId) throws Exception {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new Exception("Member를 찾을 수 없습니다."));

        return interviewRepository.findByMember(member);
    }

    // 면접 상세 조회
    @Transactional
    public Interview getInterview(Long interviewId) throws Exception {
        return interviewRepository.findByInterviewId(interviewId)
                .orElseThrow(() -> new Exception("면접 정보를 찾을 수 없습니다."));
    }

    // 기업 적합 면접 저장 + 피드백
    @Transactional
    public String companyInterviewFeedback(String memberId, CompanyInterviewDto interviewDto) throws Exception {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new Exception("Member를 찾을 수 없습니다."));

        List<Integer> feedbackAndScores = clovaService.getCompanyInterviewFeedback(interviewDto);

        // Interview 엔티티 생성
        Interview interview = Interview.builder()
                .member(member)
                .company(interviewDto.getCompanyName())
                .createDate(new Date()) // 현재 날짜를 설정합니다.
                .field(interviewDto.getField())
                .build();

        // Interview 저장
        interviewRepository.save(interview);
        return "interviewID : " + interview.getInterviewId() + " 저장 완료";
    }

    // 직무 면접 평가 기준 생성 - API
    public Map<String, String> getJobInterviewStandard() throws JsonProcessingException {
        String apiKey = "be3e0eabeb88e39ad4b7b69afa8bde25"; // API 키를 적절히 설정하세요
        String apiUrl = String.format("https://www.career.go.kr/cnet/front/openapi/job.json?apiKey=%s&seq=%s", apiKey, 1093);

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

            // 응답 상태 코드 확인
            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();

                // 응답 본문이 HTML인지 JSON인지 확인
                if (responseBody != null && responseBody.startsWith("<html")) {
                    throw new IllegalArgumentException("응답이 HTML 페이지입니다. URL이나 매개변수를 확인하세요.");
                }

                // JSON 응답 파싱
                JsonNode jsonNode = objectMapper.readTree(responseBody);

                // perform 리스트 추출 및 결합
                JsonNode performListNode = jsonNode.path("performList").path("perform");
                StringBuilder performStringBuilder = new StringBuilder();
                for (JsonNode node : performListNode) {
                    if (performStringBuilder.length() > 0) {
                        performStringBuilder.append(", ");
                    }
                    performStringBuilder.append(node.path("perform").asText());
                }
                String performCombined = performStringBuilder.toString();
                System.out.println("업무 : " + performCombined);

                // knowledge 리스트 추출 및 결합
                JsonNode knowledgeListNode = jsonNode.path("performList").path("knowledge");
                StringBuilder knowledgeStringBuilder = new StringBuilder();
                for (JsonNode node : knowledgeListNode) {
                    if (knowledgeStringBuilder.length() > 0) {
                        knowledgeStringBuilder.append(", ");
                    }
                    knowledgeStringBuilder.append(node.path("knowledge").asText());
                }
                String knowledgeCombined = knowledgeStringBuilder.toString();
                System.out.println("지식 : " + knowledgeCombined);

                // 결과를 맵에 저장
                Map<String, String> resultMap = new HashMap<>();
                resultMap.put("perform", performCombined);
                resultMap.put("knowledge", knowledgeCombined);

                return resultMap;
            } else {
                throw new IllegalArgumentException("API 호출 실패: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            // HTTP 오류 처리
            e.printStackTrace();
            throw new IllegalArgumentException("HTTP 오류: " + e.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>(); // 빈 맵 반환
        }
    }
}