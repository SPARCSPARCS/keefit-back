package com.backend.api.companyInterview.repository;

import com.backend.api.companyInterview.entity.JobInterview;
import com.backend.api.interview.entity.Interview;
import com.backend.api.member.entity.Member;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Configuration
public interface JobInterviewRepository extends JpaRepository<JobInterview, Long> {
    List<JobInterview> findByMember(Member member);
    Optional<JobInterview> findByJobInterviewId(Long jobInterviewId);
    Optional<JobInterview> findByJobInterviewIdAndMember(Long jobInterviewId, Member member);
}