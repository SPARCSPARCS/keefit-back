package com.backend.api.member.controller;

import com.backend.api.member.dto.MemberDto;
import jakarta.validation.Valid;
import com.backend.api.member.dto.MemberRequest;
import com.backend.api.member.dto.MemberResponse;
import com.backend.api.member.entity.Member;
import com.backend.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class MemberController {
    private final MemberService memberService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Member> signup(@Valid @RequestBody MemberRequest request) throws Exception {
        return new ResponseEntity<>(memberService.signup(request), HttpStatus.OK);
    }

    // 로그인
    @PostMapping(value = "/login")
    public ResponseEntity<Member> login(@RequestBody MemberRequest request) throws Exception {
        return new ResponseEntity<>(memberService.login(request), HttpStatus.OK);
    }


    // 사용자 토큰 인증 - test
//    @GetMapping("/user/get")
//    public ResponseEntity<Member> getUser(@RequestHeader("Authorization") String authorizationHeader) {
//        try {
//            // "Bearer " 접두사 제거
//            String token = authorizationHeader.startsWith("Bearer ")
//                    ? authorizationHeader.substring(7)
//                    : authorizationHeader;
//
//            // MemberService에서 토큰을 통해 사용자 정보를 가져옵니다.
//            Member member = memberService.getMemberFromToken(token);
//
//            return new ResponseEntity<>(member, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 토큰이 유효하지 않거나 오류가 발생한 경우
//        }
//    }

    // 마이페이지 정보 1 - 사용자 유형 조회
//    @GetMapping("/mypage")
//    public ResponseEntity<Integer> getTravelList(@RequestHeader("Authorization") String authorizationHeader) {
//        try {
//            // "Bearer " 접두사 제거
//            String token = authorizationHeader.startsWith("Bearer ")
//                    ? authorizationHeader.substring(7)
//                    : authorizationHeader;
//
//            // JWT 토큰을 통해 사용자 정보를 확인
////            Member member = memberService.getMemberFromToken(token);
//
//            // 서비스 ID를 사용하여 사용자 유형을 조회
////            int userType = member.getUserType();
//            return new ResponseEntity<>(userType, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 토큰이 유효하지 않거나 오류가 발생한 경우
//        }
//    }
}
