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
@RequestMapping("/interview")
public class InterviewController {
    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    private void saveFile(MultipartFile file) throws IOException {
        // 자소서 파일 저장
        Path uploadPath = Paths.get("uploads/");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(file.getOriginalFilename());
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Could not save file: " + file.getOriginalFilename(), e);
        }
    }
    // 면접 저장
    @PostMapping("{member_id}")
    public String saveInterview(
            @PathVariable("member_id") String memberId,
            @RequestBody InterviewDto interviewDto) throws Exception {
        return interviewService.saveInterview(memberId, interviewDto);
    }

    // 면접 상세 조회
    @GetMapping("{member_id}/{interview_id}")
    public Interview getInterviewDetail(
            @PathVariable("member_id") String memberId,
            @PathVariable("interview_id") Long interview_id) throws Exception {
        return interviewService.getInterview(interview_id);
    }

    // 면접 목록 조회
    @GetMapping("{member_id}/list")
    public List<Interview> getInterviewList(
            @PathVariable("member_id") String memberId) throws Exception {
        return interviewService.getInterviewList(memberId);
    }
}