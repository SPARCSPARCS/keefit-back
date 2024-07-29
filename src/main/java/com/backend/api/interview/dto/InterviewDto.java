package com.backend.api.interview.dto;

import com.backend.api.interview.entity.Interview;
import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewDto {
    private Long interviewId;

    private String companyName;

    private String memberId; // 면접을 생성한 사용자 ID

    private Date createDate;

    private String field;

    private List<String> questions;

    private List<String> answers;

    private List<String> results; // 면접 피드백
}
