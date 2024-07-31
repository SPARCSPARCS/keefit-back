package com.backend.api.companyInterview.repository;

import com.backend.api.companyInterview.entity.CompanyInterview;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Configuration
public interface CompanyInterviewRepository extends JpaRepository<CompanyInterview, Long> {
    Optional<CompanyInterview> findByCompanyInterviewId(Long companyInterviewId);
}