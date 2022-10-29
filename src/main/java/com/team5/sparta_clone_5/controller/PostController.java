package com.team5.sparta_clone_5.controller;

import com.team5.sparta_clone_5.config.UserDetailsImpl;
import com.team5.sparta_clone_5.dto.request.PostRequestDto;

import com.team5.sparta_clone_5.dto.response.GlobalResDto;
import com.team5.sparta_clone_5.dto.response.PostResponseDto;
import com.team5.sparta_clone_5.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    @PostMapping("/post")
    public GlobalResDto<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.createPost(postRequestDto,userDetails.getMember());
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
    public GlobalResDto<PostResponseDto> modifyPost(@PathVariable Long postId, @RequestBody Map<String,String> req, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.modifyPost(postId, req.get("contents"),userDetails.getMember());
    }
}
