package com.backend.api.member.controller;

import com.backend.api.global.exception.MemberException;
import com.backend.api.member.dto.MemberDto;
import jakarta.validation.Valid;
import com.backend.api.member.dto.MemberRequest;
import com.backend.api.member.dto.MemberResponse;
import com.backend.api.member.entity.Member;
import com.backend.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class MemberController {
    private final MemberService memberService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberRequest request) {
        try {
            Member member = memberService.signup(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(member);
        } catch (MemberException.MemberDuplicatedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }
    }

    // 로그인
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody MemberRequest request) {
        try {
            Member member = memberService.login(request);
            return ResponseEntity.status(HttpStatus.OK).body(member);
        } catch(MemberException.MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }
    }
}
