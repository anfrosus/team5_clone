package com.team5.sparta_clone_5.controller;

import com.team5.sparta_clone_5.dto.request.LoginRequestDto;
import com.team5.sparta_clone_5.dto.request.SignupRequestDto;
import com.team5.sparta_clone_5.dto.response.MemberResponseDto;
import com.team5.sparta_clone_5.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signUp(@RequestBody SignupRequestDto signupRequestDto) {
        return memberService.createMember(signupRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return memberService.loginMember(loginRequestDto, response);
    }
}
