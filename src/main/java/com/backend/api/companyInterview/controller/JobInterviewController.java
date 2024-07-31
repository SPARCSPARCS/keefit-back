package com.backend.api.companyInterview.controller;

import com.backend.api.companyInterview.dto.JobInterviewDto;
import com.backend.api.companyInterview.service.JobInterviewService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobInterview")
public class JobInterviewController {

    private final JobInterviewService jobInterviewService;

    public JobInterviewController(JobInterviewService jobInterviewService) {
        this.jobInterviewService = jobInterviewService;
    }

    // POST : 직무 면접 결과 저장, 피드백 생성
    @PostMapping("/{member_id}")
    public String saveJobInterview(
            @PathVariable("member_id") String memberId,
            @RequestBody JobInterviewDto interviewDto) throws Exception {
        return jobInterviewService.jobInterviewFeedback(memberId, interviewDto);
    }
}
