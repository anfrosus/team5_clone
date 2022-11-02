package com.team5.sparta_clone_5.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class SignupRequestDto {

    @NotBlank(message = "이메일을 입력해 주세요")
    @Pattern(regexp = "^[a-zA-z0-9]+@[a-zA-Z0-9]+\\.[a-z]+$", message = "이메일 형식을 확인해 주세요.")
    private String memberEmail;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Pattern(regexp = "^(?=.*[!@#$%^&*()_+=-])[a-zA-Z0-9!@#$%^&*()_+=-]*$", message = "비밀번호에 특수문자가 포함되어야 합니다.")
    private String memberPw;

    private String pwCheck;

    @NotBlank(message = "닉네임을 입력해 주세요")
    private String memberName;

}
