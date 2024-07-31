package com.backend.api.companyInterview.dto;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobInterviewResponse {
    private Long interviewId; // 전체 인터뷰 식별자

    private Long jobInterviewId; // 직무 인터뷰 식별자

    private String companyName;

    private String memberId; // 면접을 생성한 사용자 ID

    private Date createDate;

    private String field;

    private String standards; // List로 변경

    private List<String> questions;

    private List<String> answers;

    private List<String> feedback; // 면접 피드백

    private List<Integer> rate;
}
