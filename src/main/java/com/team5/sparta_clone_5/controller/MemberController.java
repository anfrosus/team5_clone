package com.team5.sparta_clone_5.controller;

import com.team5.sparta_clone_5.config.UserDetailsImpl;
import com.team5.sparta_clone_5.dto.request.LoginRequestDto;
import com.team5.sparta_clone_5.dto.request.SignupRequestDto;
import com.team5.sparta_clone_5.dto.response.MemberResponseDto;
import com.team5.sparta_clone_5.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/auth/signup")
    public ResponseEntity<MemberResponseDto> signUp(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return memberService.createMember(signupRequestDto);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<MemberResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return memberService.loginMember(loginRequestDto, response);
    }

    @PostMapping("/api/logout")
    public ResponseEntity<String> login(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memberService.logout(userDetails.getMember());
    }
}
