package com.backend.api.companyInterview.entity;

import com.backend.api.interview.entity.Interview;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyInterview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyInterviewId;

    @OneToOne
    @JoinColumn(name = "interview_id")
    private Interview interview;

    @ElementCollection
    private List<String> questions;

    @ElementCollection
    private List<String> answers;

    private String standards;

    @ElementCollection
    private List<String> feedback;

    private Integer attitudeScore;

    @ElementCollection
    private List<Integer> rate = new ArrayList<>(List.of(0, 0) );

    private Integer totalScore;
}