package com.team5.sparta_clone_5.controller;

import com.team5.sparta_clone_5.config.UserDetailsImpl;
import com.team5.sparta_clone_5.dto.request.PostReqDto2;
import com.team5.sparta_clone_5.dto.request.PostRequestDto;

import com.team5.sparta_clone_5.dto.response.GlobalResDto;
import com.team5.sparta_clone_5.dto.response.OnePostResponseDto;
import com.team5.sparta_clone_5.dto.response.PostResponseDto;
import com.team5.sparta_clone_5.service.PostService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

//    @PostMapping("/post")
//    public GlobalResDto<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto,
//                                                    @AuthenticationPrincipal UserDetailsImpl userDetails){
//        return postService.createPost(postRequestDto,userDetails.getMember());
//    }

    @PostMapping("/post")
    public GlobalResDto<PostResponseDto> createPost(MultipartHttpServletRequest imgs,
                                                    @RequestParam(required = false, value = "content")String postRequestDto,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<MultipartFile> multipartFiles = imgs.getFiles("img");

        return postService.createPost(postRequestDto,multipartFiles,userDetails.getMember());
    }


    @GetMapping("/post")
    public GlobalResDto<?> allPost(@PathVariable Long imageId){
        return postService.allPost(imageId);
    }

    @GetMapping("/post/{postId}")
    public GlobalResDto<OnePostResponseDto> onePost(@PathVariable Long postId,Long imageId,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.onePost(postId, imageId, userDetails.getMember());
    }

    @DeleteMapping("/post/{postId}")
    public GlobalResDto<PostResponseDto> delPost(@PathVariable Long postId,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.delPost(postId,userDetails.getMember());
    }

    @PatchMapping("/post/{postId}")
    public GlobalResDto<PostResponseDto> modifyPost(@PathVariable Long postId,
                                                    @RequestPart(required = false) MultipartFile file,
                                                    @RequestParam (required = false, value = "content")String content,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.modifyPost(postId, file, content ,userDetails.getMember());

    }
}
