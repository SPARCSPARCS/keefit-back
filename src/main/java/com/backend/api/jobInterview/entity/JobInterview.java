package com.backend.api.jobInterview.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobInterview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobInterviewId;

    // 직무 적합 평가 기준
    @ElementCollection
    @MapKeyColumn(name = "type")
    @Column(name = "standard")
    @CollectionTable(name = "job_interview_standard")
    private Map<String, String> standard;

    @ElementCollection
    @Column(length = 1000)  // 길이 설정
    private List<String> questions;

    @ElementCollection
    @Column(length = 1000)  // 길이 설정
    private List<String> answers;

    @ElementCollection
    private List<String> feedback;

    private Integer attitudeScore;

    @ElementCollection
    @Column(name = "score")
    private List<Integer> scores = new ArrayList<>(List.of(0, 0));

    private Integer totalScore; // 백분율로 표현
}