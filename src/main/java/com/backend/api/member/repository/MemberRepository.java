package com.backend.api.member.repository;


import com.backend.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

    Optional<Member> findOneWithAuthoritiesByUsername(String userName);
}