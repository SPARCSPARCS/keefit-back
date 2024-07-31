package com.backend.api.companyInterview.dto;

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

    private List<String> feedbacks; // 면접 피드백

    private List<Integer> scores;

    private String standards; // List로 변경
}
