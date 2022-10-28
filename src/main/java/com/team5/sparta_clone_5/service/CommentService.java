package com.team5.sparta_clone_5.service;

import com.team5.sparta_clone_5.dto.request.CommentRequestDto;
import com.team5.sparta_clone_5.dto.response.CommentResponseDto;
import com.team5.sparta_clone_5.exception.CustomException;
import com.team5.sparta_clone_5.exception.ErrorCode;
import com.team5.sparta_clone_5.model.Comment;
import com.team5.sparta_clone_5.model.Member;
import com.team5.sparta_clone_5.model.Post;
import com.team5.sparta_clone_5.repository.CommentRepository;
import com.team5.sparta_clone_5.repository.PostRepository;
import com.team5.sparta_clone_5.util.Chrono;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public ResponseEntity<CommentResponseDto> createComment(Long postId, CommentRequestDto commentRequestDto, Member currentMember) {
        Post post = postRepository.findByPostId(postId).orElseThrow(() -> new CustomException("댓글 작성", ErrorCode.NotFound));
        Comment comment = new Comment(commentRequestDto.getComment(), post, currentMember);
        commentRepository.save(comment);
        return ResponseEntity.ok(CommentResponseDto.builder()
                    .commentId(comment.getId())
                    .name(currentMember.getName())
                    .comment(comment.getComment())
                    .msg("댓글 작성 완료")
                    .createdAt(Chrono.timesAgo(comment.getCreatedAt()))
                    .build()
        );
    }

    @Transactional
    public ResponseEntity<CommentResponseDto> deleteComment(Long commentId, Member currentMember) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException("댓글 삭제", ErrorCode.NotFound));
        if (comment.getMember().getId().equals(currentMember.getId())) {
            commentRepository.deleteById(commentId);
        } else {
            throw new CustomException("댓글 작성자", ErrorCode.NotMatch);
        }
        return ResponseEntity.ok(
                CommentResponseDto.builder()
                        .commentId(commentId)
                        .msg("댓글 삭제 완료")
                        .build()
        );
    }
}
