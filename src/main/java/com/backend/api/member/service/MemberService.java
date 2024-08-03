package com.backend.api.member.service;

import com.backend.api.global.exception.MemberException;
import com.backend.api.member.dto.MemberRequest;
import com.backend.api.member.entity.Member;
import com.backend.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Member signup(MemberRequest request) {
        // memberId가 이미 존재하는 경우 예외 발생
        if (memberRepository.findByMemberId(request.getMemberId()).isPresent()) {
            throw new MemberException.MemberDuplicatedException("이미 존재하는 member ID입니다.");
        }

        Member member = Member.builder()
                .memberId(request.getMemberId())
                .build();

        memberRepository.save(member);
        return member;


    }
    public Member login(MemberRequest request) {
        Member member = memberRepository.findByMemberId(request.getMemberId())
                .orElseThrow(() -> new MemberException.MemberNotFoundException("member ID를 찾을 수 없습니다."));

        return Member.builder()
                .id(member.getId())
                .memberId(member.getMemberId())
                .build();
    }
}