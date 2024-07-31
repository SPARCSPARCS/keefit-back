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

    // 면접 상세 조회
    @Transactional
    public CompanyInterview getInterview(Long interviewId) throws Exception {
        return companyInterviewRepository.findByCompanyInterviewId(interviewId)
                .orElseThrow(() -> new Exception("면접 정보를 찾을 수 없습니다."));
    }

    // 기업 적합 면접 저장 + 피드백
    @Transactional
    public String companyInterviewFeedback(String memberId, Long interviewId, CompanyInterviewDto interviewDto) throws Exception {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new Exception("Member를 찾을 수 없습니다."));

        List<Integer> feedbackAndScores = clovaService.getCompanyInterviewFeedback(interviewDto);

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