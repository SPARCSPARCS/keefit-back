package com.backend.api.member.service;


import com.backend.api.member.dto.MemberDto;
import com.backend.api.member.dto.MemberRequest;
import com.backend.api.member.dto.MemberResponse;
import com.backend.api.member.entity.Member;
import com.backend.api.member.repository.MemberRepository;
//import com.backend.api.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtProvider jwtProvider;

    @Transactional
    public Member signup(MemberRequest request) throws Exception {
        try {
            Member member = Member.builder()
                    .memberId(request.getMemberId())
//                    .password(passwordEncoder.encode(request.getServicePw()))
                    .build();

//            member.setRoles(Collections.singletonList(Authority.builder().name("ROLE_USER").build()));

            memberRepository.save(member);
            return member;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("잘못된 요청입니다.");
        }
    }

    public Member login(MemberRequest request) throws Exception {
        Member member = memberRepository.findByMemberId(request.getMemberId())
                .orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));

//        if (!passwordEncoder.matches(request.getServicePw(), member.getPassword())) {
//            throw new BadCredentialsException("잘못된 계정정보입니다.");
//        }
//
//        if (member.getRoles() == null || member.getRoles().isEmpty()) {
//            throw new Exception("사용자의 역할이 설정되지 않았습니다.");
//        }

        return Member.builder()
                .id(member.getId())
                .memberId(member.getMemberId())
//                .token(jwtProvider.createToken(member.getUsername(), member.getRoles()))
                .build();
    }

//    @Transactional(readOnly = true)
//    public Member getMemberFromToken(String token) throws Exception {
//        // 토큰 검증 및 사용자 계정 추출
//        if (!jwtProvider.validateToken(token)) {
//            throw new Exception("유효하지 않은 토큰입니다.");
//        }
//
//        String memberId = jwtProvider.getServiceId(token);
//
//        // 사용자 정보 조회
//        return memberRepository.findByMemberId(memberId)
//                .orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));
//    }
//
//    @Transactional(readOnly = true)
//    public int getMemeberInfo(String memberId) throws Exception {
//        Member member = memberRepository.findByMemberId(memberId)
//                .orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));
//
//        // 사용자 정보 조회
//        return member.getUserType();
//    }
}