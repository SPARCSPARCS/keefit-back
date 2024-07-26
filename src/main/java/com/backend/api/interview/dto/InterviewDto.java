package com.backend.api.interview.dto;

import com.backend.api.company.dto.CompanyDto;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewDto {
    private Long interviewId;

    private String companyName;

    private List<String> questions;

    private List<String> answers;

    private List<String> results; // 면접 피드백
}
