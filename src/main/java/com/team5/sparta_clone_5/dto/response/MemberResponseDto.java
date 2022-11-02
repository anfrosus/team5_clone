package com.team5.sparta_clone_5.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberResponseDto {
    private Long memberId;
    private String msg;
}
