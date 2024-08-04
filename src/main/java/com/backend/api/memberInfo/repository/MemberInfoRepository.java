package com.backend.api.memberInfo.repository;

import com.backend.api.memberInfo.entity.MemberInfo;
import com.backend.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface MemberInfoRepository extends JpaRepository<MemberInfo, Long> {
    Optional<MemberInfo> findMemberInfoByMember(Member member);
}