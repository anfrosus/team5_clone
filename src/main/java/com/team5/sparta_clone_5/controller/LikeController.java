package com.team5.sparta_clone_5.controller;

import com.team5.sparta_clone_5.config.UserDetailsImpl;
import com.team5.sparta_clone_5.service.LikeService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/api/likes/{postId}")
    public String doLike(@PathVariable Long postId,
                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.postLike(postId, userDetails.getMember());
    }

    @GetMapping("/api/comment/likes{commentId}")
    public String commentLike(@PathVariable Long commentId,
                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        return likeService.commentLike(commentId, userDetails.getMember());
    }


}
