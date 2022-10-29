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
    public GlobalResDto createPost(@RequestPart(value = "img") MultipartFile multipartFile,
                                   @RequestParam(value = "content") String content,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        PostReqDto2 postReqDto2 = new PostReqDto2(multipartFile, content);
        return postService.createPost(postReqDto2, userDetails.getMember());
    };

    @GetMapping("/post")
    public GlobalResDto<List<PostResponseDto>> allPost(){
        return postService.allPost();
    }

    @GetMapping("/post/{postId}")
    public GlobalResDto<OnePostResponseDto> onePost(@PathVariable Long postId){
        return postService.onePost(postId);
    }

    @DeleteMapping("/post/{postId}")
    public GlobalResDto<PostResponseDto> delPost(@PathVariable Long postId,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.delPost(postId,userDetails.getMember());
    }

    @PutMapping("/post/{postId}")
    public GlobalResDto<PostResponseDto> modifyPost(@PathVariable Long postId, @RequestBody Map<String,String> req, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.modifyPost(postId, req.get("contents"),userDetails.getMember());
    }
}
