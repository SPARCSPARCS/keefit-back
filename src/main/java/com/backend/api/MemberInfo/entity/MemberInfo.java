package com.backend.api.MemberInfo.entity;

import com.backend.api.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MemberInfo")
public class MemberInfo {
    @JsonIgnore
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberInfoId;

    private Integer type;
    private String name;
    private String gender;
    private String education; // 학력 정보
    private String major; // 전공 정보

    @OneToOne
    @JoinColumn(name = "id") // 외래 키
    private Member member;
}