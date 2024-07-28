package com.backend.api.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequest {

    private Long memberId;

    private String serviceId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String servicePw;

}
