package com.backend.api.interview.entity;

import com.backend.api.companyInterview.entity.JobInterview;
import com.backend.api.jobInterview.entity.CompanyInterview;
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
    @JsonManagedReference
    private Member member;

    private Date createDate;

    private String company;

    private String field; // 직무

    // 회사 면접
    @OneToOne(mappedBy = "interview", cascade = CascadeType.ALL, orphanRemoval = true)
    private CompanyInterview companyInterview;

    // 직무 면접
    @OneToOne
    @JoinColumn(name = "jobInterview_id")
    private JobInterview jobInterview;

//    @OneToOne(mappedBy = "interview", cascade = CascadeType.ALL, orphanRemoval = true)
//    private JobInterview jobInterview;

}
