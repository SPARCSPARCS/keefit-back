package com.backend.api.jobInterview.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobInterviewDto {
    private Long interviewId; // 전체 면접 식별자

    private Long jobInterviewId; // 기업 면접 식별자

    private String field;

    private String companyName;

    private Date createdTime; // 직무 + 기업 적합 면접 공통 정보

    private List<String> standards; // 질문 생성에 사용된 뉴스 정보

    private List<String> questions;

    private List<String> answers;

    private List<String> feedback; // 면접 피드백

    private List<Integer> rate;
}
