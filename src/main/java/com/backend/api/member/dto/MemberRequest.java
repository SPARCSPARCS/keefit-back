package com.backend.api.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequest {

    private Long id;

    private String memberId;
//
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    private String servicePw;

}
