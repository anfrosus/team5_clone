package com.team5.sparta_clone_5.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CommentRequestDto {
    @NotBlank(message = "댓글을 입력해 주세요.")
    private String comment;
}
