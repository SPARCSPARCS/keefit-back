package com.backend.api.interview.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyInterviewDto {
    private Long interviewId;

    private String companyName;

    private String memberId; // 면접을 생성한 사용자 ID

    private Date createDate;

    private String field;

    private List<String> newsInfo; // 질문 생성에 사용된 뉴스 정보

    private List<String> questions;

    private List<String> answers;

    private List<String> feedback; // 면접 피드백

    private List<Integer> rate;
}
