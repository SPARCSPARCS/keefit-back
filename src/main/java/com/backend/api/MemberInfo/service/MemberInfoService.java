package com.backend.api.MemberInfo.service;

import com.backend.api.MemberInfo.dto.MemberInfoDto;
import com.backend.api.MemberInfo.entity.MemberInfo;
import com.backend.api.MemberInfo.repository.MemberInfoRepository;
import com.backend.api.member.entity.Member;
import com.backend.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberInfoService {
    private final MemberInfoRepository memberInfoRepository;
    private final MemberRepository memberRepository;

    public MemberInfo saveDetailInfo(String memberId, MemberInfoDto memberInfoDto) throws Exception {
        try {

            // member
            Member member = memberRepository.findByMemberId(memberId)
                    .orElseThrow(() -> new Exception("member를 찾을 수 없습니다."));

            // 기존의 MemberInfo를 찾거나 새로 생성합니다
            MemberInfo memberInfo = memberInfoRepository.findMemberInfoByMember(member)
                    .orElseGet(() -> {
                        MemberInfo newMemberInfo = new MemberInfo();
                        newMemberInfo.setMember(member);
                        return newMemberInfo;
                    });

            // MemberInfo를 업데이트합니다
            memberInfo.setType(memberInfoDto.getType());
            memberInfo.setName(memberInfoDto.getName());
            memberInfo.setGender(memberInfoDto.getGender());
            memberInfo.setEducation(memberInfoDto.getEducation());
            memberInfo.setMajor(memberInfoDto.getMajor());

            memberInfoRepository.save(memberInfo);

            return memberInfo;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("잘못된 요청입니다.");
        }
    }
}
