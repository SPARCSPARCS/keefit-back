package com.backend.api.companyInterview.service;

import com.backend.api.clova.ClovaService;
import com.backend.api.companyInterview.entity.CompanyInterview;
import com.backend.api.companyInterview.repository.CompanyInterviewRepository;
import com.backend.api.companyInterview.dto.CompanyInterviewDto;
import com.backend.api.interview.entity.Interview;
import com.backend.api.interview.repository.InterviewRepository;
import com.backend.api.jobInterview.entity.JobInterview;
import com.backend.api.member.entity.Member;
import com.backend.api.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyInterviewService {

    private final MemberRepository memberRepository;
    private final InterviewRepository interviewRepository;
    private final CompanyInterviewRepository companyInterviewRepository;
    private final ClovaService clovaService;

    // 기업 적합 면접 저장 + 피드백
    @Transactional
    public String companyInterviewFeedback(String memberId, Long interviewId, CompanyInterviewDto interviewDto) throws Exception {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new Exception("Member를 찾을 수 없습니다."));

        List<Integer> feedbackAndScores = clovaService.getCompanyInterviewFeedback(interviewDto);

        // 면접 점수 요청 - Clova API
        String ratePrompt = "너는 채용담당자야.\n" +
                "다음의 면접 질문과 면접 답변을 보고, 전체적인 면접에 대한 엄격하고 상세한 평가를 해줘. \n" +
                "\n" +
                "면접의 질문과 답변에 사용된 정보는 " + interviewDto.getStandard() + "이고, \n" +
                "\n" +
                "면접자가 이 정보에 대해 적절한 답변을 했는지 다음의 평가 항목으로 평가해줘. 평가는 3점 만점으로, 아주 엄격하게 진행해줘 모든 평가시에는 답변이 상세하고, 자세한지 무조건적으로 고려되어야 해.\n" +
                "\n" +
                "평가 항목 : 논리성, 내용이해도\n" +
                "\n" +
                "하나의 배열을 숫자 형식으로 출력해. 평가에 대한 부가적인 설명은 필요 없어.\n" +
                "[논리성에 대한 점수, 내용이해에 대한 점수]";

        clovaService.getJobInterviewScore(ratePrompt, interviewDto.getQuestions(), interviewDto.getAnswers());

        // Create and save CompanyInterview entity
        CompanyInterview companyInterview = CompanyInterview.builder()
                .questions(interviewDto.getQuestions())
                .answers(interviewDto.getAnswers())
                .standards(interviewDto.getStandard())
                .rate(feedbackAndScores)
                .build();

        CompanyInterview savedCompanyInterview = companyInterviewRepository.save(companyInterview);

        Interview interview = interviewRepository.findByInterviewId(interviewId)
                .orElseThrow(() -> new Exception("면접을 찾을 수 없습니다."));

        interview.setMember(member);
        interview.setCompanyInterview(savedCompanyInterview);

        // Save Interview entity
        interviewRepository.save(interview);

        return "interviewID : " + interview.getInterviewId() + " 저장 완료";
    }
}