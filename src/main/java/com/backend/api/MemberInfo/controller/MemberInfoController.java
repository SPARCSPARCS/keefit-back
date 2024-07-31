package com.backend.api.MemberInfo.controller;

import com.backend.api.MemberInfo.dto.MemberInfoDto;
import com.backend.api.MemberInfo.entity.MemberInfo;
import com.backend.api.MemberInfo.service.MemberInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberInfoController {

    private final MemberInfoService memberInfoService;

    // 사용자 상세 정보 저장
    @PostMapping(value = "/{member_id}/info")
    public ResponseEntity<MemberInfo> saveUserDetailInfo(
            @PathVariable("member_id") String memberId,
            @RequestBody MemberInfoDto memberInfoDto) throws Exception {

        MemberInfo updatedMember = memberInfoService.saveDetailInfo(memberId, memberInfoDto);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }
    // 사용자 상세 정보 조회
    @GetMapping(value = "/{member_id}/info")
    public ResponseEntity<MemberInfo> getUserDetailInfo(
            @PathVariable("member_id") String memberId) throws Exception {

        MemberInfo updatedMember = memberInfoService.getMemberInfo(memberId);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }
}
