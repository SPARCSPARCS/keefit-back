package com.backend.api.member.entity;

import com.backend.api.MemberInfo.entity.MemberInfo;
import com.backend.api.interview.entity.Interview;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "Member")
public class Member {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 식별자

    @Column(unique = true)
    private String memberId;

//    private String password;

//    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @Builder.Default
//    private List<Authority> roles = new ArrayList<>();
//
//    public void setRoles(List<Authority> role) {
////        this.roles = role;
//        role.forEach(o -> o.setMember(this));
//    }

}