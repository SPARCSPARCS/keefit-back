package com.backend.api.member.dto;

import com.backend.api.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {
    private Long id;
    private String username;

    private String token;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
    }

}