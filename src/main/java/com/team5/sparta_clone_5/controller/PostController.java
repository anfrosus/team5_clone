package com.team5.sparta_clone_5.controller;

import com.team5.sparta_clone_5.config.UserDetailsImpl;
import com.team5.sparta_clone_5.dto.request.PostRequestDto;

import com.team5.sparta_clone_5.dto.response.GlobalResDto;
import com.team5.sparta_clone_5.dto.response.PostResponseDto;
import com.team5.sparta_clone_5.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    @PostMapping("/post")
    public GlobalResDto<PostResponseDto> createPost(@RequestPart(required = false) List<MultipartFile> file,
                                                    @RequestParam(required = false, value = "postRequestDto")String postRequestDto,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.createPost(postRequestDto,file,userDetails.getMember());
    }

    @GetMapping("/post")
    public GlobalResDto<List<PostResponseDto>> allPost(){
        return postService.allPost();
    }

    @GetMapping("/post/{postId}")
    public GlobalResDto<PostResponseDto> onePost(@PathVariable Long postId){
        return postService.onePost(postId);
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
