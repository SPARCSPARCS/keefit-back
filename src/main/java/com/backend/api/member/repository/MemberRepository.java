package com.backend.api.member.repository;


import com.backend.api.interview.entity.Interview;
import com.backend.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberId(String memberId);

//    Optional<Member> findOneWithAuthoritiesByUsername(String userName);
}