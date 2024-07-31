package com.backend.api.interview.dto;

import com.backend.api.jobInterview.dto.CompanyInterviewDto;
import com.backend.api.companyInterview.dto.JobInterviewDto;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewDto {
    private Long interviewId; // 전체 면접 식별자

    private String memberId;

    private String  field;

    private String Company;

    private Date createdTime; // 직무 + 기업 적합 면접 공통 정보

    private JobInterviewDto jobInterviewDto;

    private CompanyInterviewDto companyInterviewDto;

}
