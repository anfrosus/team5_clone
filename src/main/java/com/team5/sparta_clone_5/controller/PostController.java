package com.team5.sparta_clone_5.controller;

import com.team5.sparta_clone_5.dto.request.PostRequestDto;
import com.team5.sparta_clone_5.dto.request.service.PostService;
import com.team5.sparta_clone_5.dto.response.GlobalResDto;
import com.team5.sparta_clone_5.dto.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    @PostMapping("/post/create")
    public GlobalResDto<PostResponseDto> createPost(@RequestMapping PostRequestDto postRequestDto){
        return postService.createPost(postRequestDto);
    }

    @GetMapping("/post/all")
    public GlobalResDto<PostResponseDto> allPost(){
        return postService.allPost();
    }

    @GetMapping("/post/one/{postId}")
    public GlobalResDto<PostResponseDto> onePost(@PathVariable Long postId){
        return postService.onePost(postId);
    }

    @DeleteMapping("/post/delete/{postId}")
    public GlobalResDto<PostResponseDto> delPost(@PathVariable Long postId){
        return postService.delPost(postId);
    }

    @PutMapping("/post/upPost/{postId}")
    public GlobalResDto<PostResponseDto> modifyPost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto){
        return postService.modifyPost(postId,postRequestDto);
    }
}
