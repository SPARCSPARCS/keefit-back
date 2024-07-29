package com.backend.api.company.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(
        name = "Company.findByCompanyName",
        query = "SELECT c FROM Company c WHERE c.companyName = :companyName"
)
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    private String companyName;

    @ElementCollection
    private List<String> slogans; // 기업별 인재상
}
