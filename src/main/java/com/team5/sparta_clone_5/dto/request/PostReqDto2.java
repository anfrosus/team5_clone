package com.team5.sparta_clone_5.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class PostReqDto2 {
    private MultipartFile img;
    private String content;
}
