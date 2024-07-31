package com.backend.api.interview.entity;

import com.backend.api.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interviewId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonManagedReference // 무한 루프 방지를 위해 추가
    private Member member;

    private String company;

    private Date createDate;

    private String field; // 직무

    private String fileName;  // 자소서 파일명

    // 면접 평가 기준
    @ElementCollection
    @MapKeyColumn(name = "type")
    @Column(name = "standard")
    @CollectionTable(name = "interview_standard")
    private Map<String, String> standard;

    private String standard2;

    // 직무 관련 면접 질문
    @ElementCollection
    @Column(length = 1000)  // 길이 설정
    private List<String> questions;

    // 직무 면접 관련 답변
    @ElementCollection
    @Column(length = 1000)  // 길이 설정
    private List<String> answers;

    // 직무 면접 피드백
    @ElementCollection
    @Column(length = 1000)  // 길이 설정
    private List<String> feedback;

    // 직무 면접 태도 평가
    @ElementCollection
    @Column(length = 1000)
    private List<String> attitudeFeedback;

    // 직무 면접 평가 점수
    @ElementCollection
    private List<Integer> rate;
}
