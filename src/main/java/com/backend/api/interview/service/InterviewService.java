package com.backend.api.interview.service;

import com.backend.api.interview.entity.Interview;
import com.backend.api.interview.repository.InterviewRepository;
import com.backend.api.member.entity.Member;
import com.backend.api.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class InterviewService {

    private final MemberRepository memberRepository;
    private final InterviewRepository interviewRepository;

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
}