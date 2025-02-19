package com.backend.api.memberInfo.service;

import com.backend.api.memberInfo.dto.MemberInfoDto;
import com.backend.api.memberInfo.entity.MemberInfo;
import com.backend.api.memberInfo.repository.MemberInfoRepository;
import com.backend.api.member.entity.Member;
import com.backend.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberInfoService {
    private final MemberInfoRepository memberInfoRepository;
    private final MemberRepository memberRepository;

    public MemberInfo saveDetailInfo(String memberId, MemberInfoDto memberInfoDto) throws Exception {
        try {

            // member 검증
            Member member = memberRepository.findByMemberId(memberId)
                    .orElseThrow(() -> new Exception("member를 찾을 수 없습니다."));

            // memberInfo 없는 경우 새로 생성
            MemberInfo memberInfo = memberInfoRepository.findMemberInfoByMember(member)
                    .orElseGet(() -> {
                        MemberInfo newMemberInfo = new MemberInfo();
                        newMemberInfo.setMember(member);
                        return newMemberInfo;
                    });

            // memberInfo 업데이트
            memberInfo.setName(memberInfoDto.getName());
            memberInfo.setMajor(memberInfoDto.getMajor());

            memberInfoRepository.save(memberInfo);

            return memberInfo;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("잘못된 요청입니다.");
        }
    }

    // 사용자 상세 정보 조회
    @Transactional
    public MemberInfo getMemberInfo(String memberId) throws Exception {
        try {
            // member 검증
            Member member = memberRepository.findByMemberId(memberId)
                    .orElseThrow(() -> new Exception("member를 찾을 수 없습니다."));

            // memberInfo 검증
            return memberInfoRepository.findMemberInfoByMember(member)
                    .orElseThrow(() -> new Exception("member를 찾을 수 없습니다."));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("잘못된 요청입니다.");
        }
    }
}
