package com.backend.api.interview.dto;

import com.backend.api.company.dto.CompanyDto;
import com.backend.api.interview.entity.Interview;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewResponse {
    private Long interviewId;

    private String companyName;

    private String field; // 직무 정보
    private List<String> questions;

    public InterviewResponse(Interview interview) {
        this.interviewId = interview.getInterviewId();
        this.companyName = interview.getCompany();
        this.field = interview.getField();
        this.questions = interview.getQuestions();
    }
}
