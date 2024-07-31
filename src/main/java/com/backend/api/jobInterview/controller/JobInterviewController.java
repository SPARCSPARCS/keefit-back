package com.backend.api.jobInterview.controller;

import com.backend.api.interview.entity.Interview;
import com.backend.api.jobInterview.dto.JobInterviewDto;
import com.backend.api.jobInterview.entity.JobInterview;
import com.backend.api.jobInterview.service.JobInterviewService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class JobInterviewController {

    private final JobInterviewService jobInterviewService;

    public JobInterviewController(JobInterviewService jobInterviewService) {
        this.jobInterviewService = jobInterviewService;
    }

    // GET : 직무 면접 상세
    @GetMapping("/jobInterview/{member_id}/{interview_id}")
    public JobInterview getInterviewDetail(
            @PathVariable("member_id") String memberId,
            @PathVariable("interview_id") Long interview_id) throws Exception {
        return jobInterviewService.getInterview(interview_id);
    }

    // POST : 직무 면접 결과 저장, 피드백 생성
    @PostMapping("/interview/{member_id}")
    public String saveJobInterview(
            @PathVariable("member_id") String memberId,
            @RequestBody JobInterviewDto interviewDto) throws Exception {
        return jobInterviewService.jobInterviewFeedback(memberId, interviewDto);
    }
}
