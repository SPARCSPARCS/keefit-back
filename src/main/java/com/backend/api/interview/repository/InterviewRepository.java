package com.backend.api.interview.repository;

import com.backend.api.company.entity.Company;
import com.backend.api.interview.entity.Interview;
import com.backend.api.member.entity.Member;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Configuration
public interface InterviewRepository extends JpaRepository<Interview, Long> {
    List<Interview> findByMember(Member member);
    Optional<Interview> findByInterviewId(Long interviewId);
    Optional<Interview> findByInterviewIdAndMember(Long interviewId, Member member);
}