package com.backend.api.companyInterview.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobInterviewFeedbackRequest {
    private Long interviewId; // 전체 인터뷰 식별자

    private Long jobInterviewId; // 직무 인터뷰 식별자

    private String companyName;

    private String field;

    private String standards; // List로 변경

    private List<String> questions;

    private List<String> answers;
}
