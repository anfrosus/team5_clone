package com.team5.sparta_clone_5.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {
    private String email;
    private String name;
    private String contents;
    private String img;
}
