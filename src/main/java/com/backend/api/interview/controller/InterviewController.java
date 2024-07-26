package com.backend.api.interview.controller;

import com.backend.api.interview.dto.InterviewRequest;
import com.backend.api.interview.dto.InterviewResponse;
import com.backend.api.interview.entity.Interview;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.backend.api.interview.service.InterviewService;

@RestController
@RequestMapping("/interview")
public class InterviewController {
    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    // 면접 질문 생성 요청
    @PostMapping("")
    public ResponseEntity<InterviewResponse> createInterview(@RequestBody InterviewRequest request) {
        try {
            Interview interview = interviewService.createInterview(request);
            InterviewResponse response = new InterviewResponse(interview);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            // 예외 처리 및 로깅
            System.out.println("Error creating interview: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
