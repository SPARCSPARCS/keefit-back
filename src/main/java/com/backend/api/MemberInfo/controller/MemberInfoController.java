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
public class MemberInfoController {

    private final MemberInfoService memberInfoService;

    // 사용자 상세 정보 입력
    @PostMapping(value = "/{member_id}/info")
    public ResponseEntity<MemberInfo> updateUserDetailInfo(
            @PathVariable("member_id") String memberId,
            @RequestBody MemberInfoDto memberInfoDto) throws Exception {
        // memberId와 memberDto를 사용하여 상세 정보를 저장하는 서비스 호출
        MemberInfo updatedMember = memberInfoService.saveDetailInfo(memberId, memberInfoDto);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }
}
