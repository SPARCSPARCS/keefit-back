package com.backend.api.interview.service;

import com.backend.api.company.repository.CompanyRepository;
import com.backend.api.interview.dto.InterviewRequest;
import com.backend.api.interview.dto.InterviewResponse;
import com.backend.api.interview.entity.Interview;
import com.backend.api.company.entity.Company;
import com.backend.api.interview.repository.InterviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InterviewService {

    private final CompanyRepository companyRepository;
    private final InterviewRepository interviewRepository;

    @Transactional
    public Interview createInterview(InterviewRequest request, String fileName) throws Exception {
        // FileName 저장 로직 등 추가

        List<String> generatedQuestions = generateQuestions(request);

        Interview interview = Interview.builder()
                .company(request.getCompanyName())
                .field(request.getField())
                .questions(generatedQuestions)
                .fileName(fileName) // 파일 이름 저장
                .build();

        return interviewRepository.save(interview);
    }

    private List<String> generateQuestions(InterviewRequest request) {
        // Clova API를 사용하여 질문 생성
        // 이 부분은 실제 Clova API 호출 로직으로 대체되어야 합니다.
        // 예시: List<String> questions = clovaService.generateQuestions(company, info, field);
        List<String> questions = new ArrayList<>();
        questions.add("Generated interview questions from Clova");
        return questions;
    }
}
