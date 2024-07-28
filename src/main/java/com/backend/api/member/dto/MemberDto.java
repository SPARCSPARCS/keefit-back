package com.backend.api.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

    private Long memberId;

    private String serviceId;

    private String userType;

}
