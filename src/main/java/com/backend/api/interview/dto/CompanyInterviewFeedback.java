package com.backend.api.interview.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyInterviewFeedback {
    private List<String> questions;

    private List<String> answers;

    private List<String> standard;

    private List<String> feedbacks; // 면접 피드백

    private List<Integer> scores;

    private List<Integer> jobFit; // 업무 적합도 평가

    private List<Integer> companyFit; // 기업 적합도 평가
}