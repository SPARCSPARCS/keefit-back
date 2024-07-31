package com.backend.api.interview.controller;

import com.backend.api.interview.dto.InterviewDto;
import com.backend.api.interview.entity.Interview;
import org.springframework.web.bind.annotation.*;
import com.backend.api.interview.service.InterviewService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("")
public class InterviewController {
    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    // 직무 면접 결과 저장
    @PostMapping("/interview/{member_id}")
    public String saveInterview(
            @PathVariable("member_id") String memberId,
            @RequestBody InterviewDto interviewDto) throws Exception {
        return interviewService.saveInterview(memberId, interviewDto);
    }

    // 면접 상세 조회
    @GetMapping("/interview/{member_id}/{interview_id}")
    public Interview getInterviewDetail(
            @PathVariable("member_id") String memberId,
            @PathVariable("interview_id") Long interview_id) throws Exception {
        return interviewService.getInterview(interview_id);
    }

    // 면접 목록 조회
    @GetMapping("/interview/{member_id}/list")
    public List<Interview> getInterviewList(
            @PathVariable("member_id") String memberId) throws Exception {
        return interviewService.getInterviewList(memberId);
    }

    // 직무 면접 결과 저장
    @PostMapping("/jobInterview/{member_id}/{job}")
    public String saveJobInterview(
            @PathVariable("member_id") String memberId,
            @PathVariable("job") String job,
            @RequestBody InterviewDto interviewDto) throws Exception {
        return interviewService.jobinterviewFeedback(memberId, interviewDto, job);
    }

    // 직무 면접 조회
    @GetMapping("/jobInterview/{member_id}/{interview_id}")
    public Interview getJobInterviewDetail(
            @PathVariable("member_id") String memberId,
            @PathVariable("interview_id") Long interviewId) throws Exception {
        return interviewService.getInterview(interviewId);
    }
}