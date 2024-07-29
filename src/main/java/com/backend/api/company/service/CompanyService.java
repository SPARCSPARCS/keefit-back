package com.backend.api.company.service;

import com.backend.api.company.entity.Company;
import com.backend.api.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> findCompaniesByName(String companyName) {
        return companyRepository.findByCompanyName(companyName);
    }
}