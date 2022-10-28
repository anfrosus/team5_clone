package com.team5.sparta_clone_5.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequestDto {

    @NotBlank(message = "이메일을 입력해 주세요")
    private String memberEmail;

    @NotBlank(message = "패스워드를 입력해 주세요")
    private String memberPw;
}
