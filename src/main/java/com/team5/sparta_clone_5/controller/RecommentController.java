package com.team5.sparta_clone_5.controller;

import com.team5.sparta_clone_5.config.UserDetailsImpl;
import com.team5.sparta_clone_5.dto.request.CommentRequestDto;
import com.team5.sparta_clone_5.dto.response.CommentResponseDto;
import com.team5.sparta_clone_5.dto.response.RecommentResDto;
import com.team5.sparta_clone_5.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recomment")
public class RecommentController {
    private final CommentService commentService;

    @PostMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> createRecomment(@PathVariable Long commentId,
                                                              @RequestBody @Valid CommentRequestDto commentRequestDto,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createRecomment(commentId, commentRequestDto, userDetails.getMember());
    }

    @DeleteMapping("/{recommentId}")
    public ResponseEntity<CommentResponseDto> deleteRecomment(@PathVariable Long recommentId,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteRecomment(recommentId, userDetails.getMember());
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<List<RecommentResDto>> getRecomment(@PathVariable Long commentId) {
        return commentService.selectRecomment(commentId);
    }
}
