package com.backend.api.interview.entity;

import com.backend.api.company.entity.Company;
import com.backend.api.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

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

    @ElementCollection
    @Column(length = 1000)  // 길이를 적절히 설정합니다
    private List<String> questions;

    @ElementCollection
    @Column(length = 1000)  // 길이를 적절히 설정합니다
    private List<String> answers;

    @ElementCollection
    @Column(length = 1000)  // 길이를 적절히 설정합니다
    private List<String> feedback;

    @ElementCollection
    private List<Integer> rate;
}
