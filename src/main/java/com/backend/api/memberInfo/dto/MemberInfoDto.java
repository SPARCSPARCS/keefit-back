package com.backend.api.memberInfo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfoDto {

    @NotBlank(message = "이름은 필수 항목입니다.")
    private String name;
    @NotBlank
    private String education; // 학력 정보
    @NotBlank
    private String major; // 전공 정보
}
