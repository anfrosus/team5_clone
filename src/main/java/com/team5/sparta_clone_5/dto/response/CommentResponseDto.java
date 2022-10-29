package com.team5.sparta_clone_5.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDto {
    private Long commentId;
    private String name;
    private String comment;
    private String msg;
    private String createdAt;
}
