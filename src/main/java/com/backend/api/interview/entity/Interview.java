package com.backend.api.interview.entity;

import com.backend.api.company.entity.Company;
import com.backend.api.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

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

//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    @JsonManagedReference // 무한 루프 방지를 위해 추가
//    private Member member;

    private String company;

    private String field; // 직무

    private String fileName;  // 자소서 파일명

    @ElementCollection
    private List<String> questions;

    @ElementCollection
    private List<String> answers;

    private String feedback;
}
