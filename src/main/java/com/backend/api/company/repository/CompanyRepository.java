package com.backend.api.company.repository;

import com.backend.api.company.entity.Company;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Configuration
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query(name = "Company.findByCompanyName")
    List<Company> findByCompanyName(@Param("companyName") String companyName);
}