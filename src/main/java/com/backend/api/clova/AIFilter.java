package com.backend.api.clova;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AIFilter {
    @JsonProperty("groupName")
    private String groupName;
    @JsonProperty("name")
    private String name;
    @JsonProperty("score")
    private String score;
}
