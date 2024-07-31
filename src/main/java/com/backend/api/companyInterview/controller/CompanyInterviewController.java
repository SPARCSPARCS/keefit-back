package com.backend.api.companyInterview.controller;

import com.backend.api.companyInterview.dto.CompanyInterviewDto;
import com.backend.api.companyInterview.service.CompanyInterviewService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interview")
public class CompanyInterviewController {
    private final CompanyInterviewService companyInterviewService;

    public CompanyInterviewController(CompanyInterviewService companyInterviewService) {
        this.companyInterviewService = companyInterviewService;
    }

    // POST : 기업 적합 면접 결과 저장, 피드백 생성
    @PostMapping("/{member_id}/{interview_id}")
    public String saveCompanyInterview(
            @PathVariable("member_id") String memberId,
            @PathVariable("interview_id") Long interviewId,
            @RequestBody CompanyInterviewDto interviewDto) throws Exception {
        return companyInterviewService.companyInterviewFeedback(memberId, interviewId, interviewDto);
    }

}