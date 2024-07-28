package com.backend.api.interview.controller;

import com.backend.api.interview.dto.InterviewRequest;
import com.backend.api.interview.dto.InterviewResponse;
import com.backend.api.interview.entity.Interview;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.backend.api.interview.service.InterviewService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/interview")
public class InterviewController {
    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    // 면접 질문 생성 요청
    @PostMapping("")
    public ResponseEntity<InterviewResponse> createInterview(@RequestPart("request") InterviewRequest request) {
//        @RequestPart("request") InterviewRequest request,
//        @RequestPart("file") MultipartFile file
//        try {
//            String fileName = file.getOriginalFilename();
//            // 자소서 파일 저장
//            saveFile(file);
//
//            // 면접 생성
//            Interview interview = interviewService.createInterview(request, fileName);
//            InterviewResponse response = new InterviewResponse(interview);
//            return new ResponseEntity<>(response, HttpStatus.CREATED);
//        } catch (Exception e) {
//            // 예외 처리 및 로깅
//            System.out.println("Error creating interview: " + e.getMessage());
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }

        try {
            // 면접 생성
            Interview interview = interviewService.createInterview(request);
            InterviewResponse response = new InterviewResponse(interview);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            // 예외 처리 및 로깅
            System.out.println("Error creating interview: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
//
//    private void saveFile(MultipartFile file) throws IOException {
//        // 자소서 파일 저장
//        Path uploadPath = Paths.get("uploads/");
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//        try (InputStream inputStream = file.getInputStream()) {
//            Path filePath = uploadPath.resolve(file.getOriginalFilename());
//            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            throw new IOException("Could not save file: " + file.getOriginalFilename(), e);
//        }
//    }
}
