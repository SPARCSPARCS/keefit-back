package com.backend.api.MemberInfo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfoDto {
    private String name;
    private String education; // 학력 정보
    private String major; // 전공 정보
}
