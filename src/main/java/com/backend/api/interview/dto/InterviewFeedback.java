package com.backend.api.interview.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewFeedback {
    private List<String> questions;

    private List<String> answers;

    private List<String> feedbacks; // 면접 피드백

    private List<Integer> scores;
}
