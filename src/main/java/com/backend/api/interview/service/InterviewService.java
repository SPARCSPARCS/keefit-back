package com.backend.api.interview.service;

//import com.backend.api.clova.ClovaService;
import com.backend.api.clova.ClovaService;
import com.backend.api.interview.dto.InterviewDto;
import com.backend.api.interview.dto.InterviewFeedback;
import com.backend.api.interview.entity.Interview;
import com.backend.api.interview.repository.InterviewRepository;
import com.backend.api.member.entity.Member;
import com.backend.api.member.repository.MemberRepository;
import com.backend.api.utils.FileContentReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class InterviewService {

    private final MemberRepository memberRepository;
    private final InterviewRepository interviewRepository;
    private final ClovaService clovaService;

    private final FileContentReader fileContentReader = new FileContentReader();
    // clova 문서 요약 API
    private static final String CLOVA_API_URL = "https://naveropenapi.apigw.ntruss.com/text-summary/v1/summarize";

    @Value("${naver.clova.clientid}")
    private String CLIENTID;

    @Value("${naver.clova.secretkey}")
    private String SECRETKEY;

    private final ObjectMapper objectMapper = new ObjectMapper();

//    // 파일 read
//    private final Tika tika = new Tika();

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

    // 면접 저장
    @Transactional
    public String saveInterview(String memberId, InterviewDto interviewDto) throws Exception {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new Exception("Member를 찾을 수 없습니다."));

        // ClovaService에서 피드백과 점수를 가져옵니다.
        InterviewFeedback feedbackAndScores = clovaService.getInterviewFeedbackAndScore(interviewDto);

        // Interview 엔티티 생성
        Interview interview = Interview.builder()
                .company(interviewDto.getCompanyName())
                .createDate(new Date()) // 현재 날짜를 설정합니다.
                .field(interviewDto.getField())
                .questions(interviewDto.getQuestions())
                .answers(interviewDto.getAnswers())
                .feedback(feedbackAndScores.getFeedbacks()) // 피드백 리스트 설정
                .rate(feedbackAndScores.getScores()) // 점수 리스트 설정
                .build();

        // Interview 저장
        interviewRepository.save(interview);

        return "저장 완료";
    }
//
//    public List<String> generateQuestions(InterviewRequest request, String fileName) throws IOException {
//        Path filePath = Paths.get("uploads/", fileName);
//        String fileContent = fileContentReader.readFileContentAsText(filePath);
//        String summary = summarizeText(fileContent);
//
//        // 자소서 요약내용으로 질문 생성
//        List<String> questions = new ArrayList<>();
//        questions.add("Generated interview questions based on summary: " + summary);
//        return questions;
//    }
////
////    public String summarizeText(String content) throws IOException {
////        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
////            HttpPost httpPost = new HttpPost(CLOVA_API_URL);
////            httpPost.setHeader("X-NCP-APIGW-API-KEY-ID", CLIENTID);
////            httpPost.setHeader("X-NCP-APIGW-API-KEY", SECRETKEY);
////            httpPost.setHeader("Content-Type", "application/json");
////
////            // Create JSON body
////            String jsonBody = createInterviewJsonBody(content);
////            HttpEntity stringEntity = new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
////            httpPost.setEntity(stringEntity);
////
////            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
////                HttpEntity responseEntity = response.getEntity();
////                String responseBody = EntityUtils.toString(responseEntity);
////                return extractSummaryFromResponse(responseBody);
////            }
////        }
////    }
//
//    private String createInterviewJsonBody(List<String> questions, List<String> answers) throws IOException {
//        // 클로바 프롬프트 생성
//        String clovaPrompt = "면접을 준비하는 지원자를 위한 면접 질문을 3개 생성해주세요. 제공해드릴 데이터는 희망 직무, 회사 채용 정보입니다."
//                + "희망하는 직무는 " + request.getField() + "이며, 지원하는 회사의 채용 정보는" + request.getInfo() + "입니다. "
//                + "직무와 관련된 기술 질문 1개, 채용 공고를 참고하여 질문 1개, 지원자의 인성을 알 수 있는 인성 질문 1개를 생성해주세요.";
//
//        ChatMessage systemMessage = new ChatMessage("system", clovaPrompt);
//        List<ChatMessage> messages = new ArrayList<>();
//        messages.add(systemMessage);
//
//        // Create the request body
//        ClovaRequest clovaRequest = new ClovaRequest(messages, 0.5, 0, 0.8, 5.0, null, 100, false, 0);
//        return objectMapper.writeValueAsString(clovaRequest);
//    }

//    private String extractSummaryFromResponse(String responseBody) {
//        try {
//            JsonNode rootNode = objectMapper.readTree(responseBody);
//            JsonNode summaryNode = rootNode.path("summary");
//            return summaryNode.asText().trim();
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to parse response JSON", e);
//        }
//    }

}