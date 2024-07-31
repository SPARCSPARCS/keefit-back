package com.backend.api.jobInterview.repository;

import com.backend.api.interview.entity.Interview;
import com.backend.api.jobInterview.entity.JobInterview;
import com.backend.api.member.entity.Member;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Configuration
public interface JobInterviewRepository extends JpaRepository<JobInterview, Long> {
    Optional<JobInterview> findByJobInterviewId(Long interviewId);
}