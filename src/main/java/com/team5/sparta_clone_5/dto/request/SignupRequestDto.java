package com.team5.sparta_clone_5.dto.request;

import lombok.Getter;

@Getter
public class SignupRequestDto {
    private String memberEmail;
    private String memberPw;
    private String pwCheck;
    private String memberName;
}
