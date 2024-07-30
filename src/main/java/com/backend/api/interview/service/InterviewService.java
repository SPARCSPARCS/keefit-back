package com.backend.api.interview.service;

import com.backend.api.clova.ClovaService;
import com.backend.api.interview.dto.InterviewDto;
import com.backend.api.interview.dto.InterviewFeedback;
import com.backend.api.interview.entity.Interview;
import com.backend.api.interview.repository.InterviewRepository;
import com.backend.api.member.entity.Member;
import com.backend.api.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}