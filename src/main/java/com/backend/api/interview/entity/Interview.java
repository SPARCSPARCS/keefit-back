package com.backend.api.interview.entity;

import com.backend.api.company.entity.Company;
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

    private String company;

    private String field; // 직무

    private String fileName;  // 자소서 파일명

    @ElementCollection
    private List<String> questions;

    @ElementCollection
    private List<String> answers;

    private String feedback;
}
