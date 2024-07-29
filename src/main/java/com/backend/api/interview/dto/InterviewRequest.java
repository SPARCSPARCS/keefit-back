package com.backend.api.interview.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewRequest {
    private String companyName;
    private String serviceId; // 사용자 ID
    private String info; // 채용정보
    private String field; // 직무정보
}
