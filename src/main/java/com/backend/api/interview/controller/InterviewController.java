package com.backend.api.interview.controller;

import com.backend.api.interview.entity.Interview;
import org.springframework.web.bind.annotation.*;
import com.backend.api.interview.service.InterviewService;

import java.util.List;

@RestController
@RequestMapping("/interview")
public class InterviewController {
    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    // GET : 면접 상세
    @GetMapping("/{member_id}/{interview_id}")
    public Interview getInterviewDetail(
            @PathVariable("member_id") String memberId,
            @PathVariable("interview_id") Long interview_id) throws Exception {
        return interviewService.getInterview(interview_id);
    }

    // GET : 면접 목록
    @GetMapping("/{member_id}/list")
    public List<Interview> getInterviewList(
            @PathVariable("member_id") String memberId) throws Exception {
        return interviewService.getInterviewList(memberId);
    }
}