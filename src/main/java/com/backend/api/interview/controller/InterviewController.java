package com.backend.api.interview.controller;

import com.backend.api.jobInterview.dto.CompanyInterviewDto;
import com.backend.api.companyInterview.dto.JobInterviewDto;
import com.backend.api.interview.entity.Interview;
import org.springframework.web.bind.annotation.*;
import com.backend.api.interview.service.InterviewService;

import java.util.List;

@RestController
@RequestMapping("")
public class InterviewController {
    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    // GET : 면접 상세
    @GetMapping("/interview/{member_id}/{interview_id}")
    public Interview getInterviewDetail(
            @PathVariable("member_id") String memberId,
            @PathVariable("interview_id") Long interview_id) throws Exception {
        return interviewService.getInterview(interview_id);
    }

    // GET : 면접 목록
    @GetMapping("/interview/{member_id}/list")
    public List<Interview> getInterviewList(
            @PathVariable("member_id") String memberId) throws Exception {
        return interviewService.getInterviewList(memberId);
    }

    // POST : 직무 면접 결과 저장, 피드백 생성
    @PostMapping("/interview/{member_id}")
    public String saveJobInterview(
            @PathVariable("member_id") String memberId,
            @RequestBody JobInterviewDto interviewDto) throws Exception {
        return interviewService.jobInterviewFeedback(memberId, interviewDto);
    }

    // POST : 기업 적합 면접 결과 저장, 피드백 생성
    @PostMapping("/interview2/{member_id}")
    public String saveCompanyInterview(
            @PathVariable("member_id") String memberId,
            @RequestBody CompanyInterviewDto interviewDto) throws Exception {
        return interviewService.companyInterviewFeedback(memberId, interviewDto);
    }

}