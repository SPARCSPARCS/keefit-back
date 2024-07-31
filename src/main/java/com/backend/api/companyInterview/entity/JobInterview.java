package com.backend.api.companyInterview.entity;

import com.backend.api.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

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

    @OneToOne
    @JoinColumn(name = "member_id")
    @JsonManagedReference
    private Member member;

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

    @ElementCollection
    private List<String> attitudeFeedback;

    @ElementCollection
    private List<Integer> rate;
}