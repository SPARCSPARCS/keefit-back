package com.backend.api.member.controller;

import jakarta.validation.Valid;
import com.backend.api.member.dto.MemberRequest;
import com.backend.api.member.dto.MemberResponse;
import com.backend.api.member.entity.Member;
import com.backend.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Member> signup(
            @Valid @RequestBody MemberRequest memberRequestDTO) throws Exception {
        return new ResponseEntity<>(memberService.signup(memberRequestDTO), HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<MemberResponse> login(@RequestBody MemberRequest request) throws Exception {
        return new ResponseEntity<>(memberService.login(request), HttpStatus.OK);
    }

    @GetMapping("/user/get")
    public ResponseEntity<Member> getUser(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            // "Bearer " 접두사 제거
            String token = authorizationHeader.startsWith("Bearer ")
                    ? authorizationHeader.substring(7)
                    : authorizationHeader;

            // MemberService에서 토큰을 통해 사용자 정보를 가져옵니다.
            Member member = memberService.getMemberFromToken(token);

            return new ResponseEntity<>(member, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 토큰이 유효하지 않거나 오류가 발생한 경우
        }
    }

}