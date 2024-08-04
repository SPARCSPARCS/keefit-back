package com.backend.api.memberInfo.controller;

import com.backend.api.global.exception.MemberException;
import com.backend.api.member.entity.Member;
import com.backend.api.memberInfo.dto.MemberInfoDto;
import com.backend.api.memberInfo.entity.MemberInfo;
import com.backend.api.memberInfo.service.MemberInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class MemberInfoController {

    private final MemberInfoService memberInfoService;

    // 사용자 상세 정보 저장
    @PostMapping(value = "/{member_id}/info")
    public ResponseEntity<?> saveUserDetailInfo(
            @Valid @PathVariable("member_id") String memberId,
            @Valid @RequestBody MemberInfoDto memberInfoDto) throws Exception {

        try {
            MemberInfo updatedMember = memberInfoService.saveDetailInfo(memberId, memberInfoDto);
            return new ResponseEntity<>(updatedMember, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }
    }
    // 사용자 상세 정보 조회
    @GetMapping(value = "/{member_id}/info")
    public ResponseEntity<MemberInfo> getUserDetailInfo(
            @PathVariable("member_id") String memberId) throws Exception {

        MemberInfo updatedMember = memberInfoService.getMemberInfo(memberId);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }
}
