package com.team5.sparta_clone_5.service;

import com.team5.sparta_clone_5.config.UserDetailsImpl;
import com.team5.sparta_clone_5.dto.request.CommentRequestDto;
import com.team5.sparta_clone_5.dto.response.CommentResponseDto;
import com.team5.sparta_clone_5.dto.response.RecommentResDto;
import com.team5.sparta_clone_5.exception.CustomException;
import com.team5.sparta_clone_5.exception.ErrorCode;
import com.team5.sparta_clone_5.model.Comment;
import com.team5.sparta_clone_5.model.Member;
import com.team5.sparta_clone_5.model.Post;
import com.team5.sparta_clone_5.model.Recomment;
import com.team5.sparta_clone_5.repository.CommentRepository;
import com.team5.sparta_clone_5.repository.PostRepository;
import com.team5.sparta_clone_5.repository.RecommentLikeRepository;
import com.team5.sparta_clone_5.repository.RecommentRepository;
import com.team5.sparta_clone_5.util.Chrono;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final RecommentRepository recommentRepository;
    private final RecommentLikeRepository recommentLikeRepository;

    @Transactional
    public ResponseEntity<CommentResponseDto> createComment(Long postId, CommentRequestDto commentRequestDto, Member currentMember) {
        Post post = postRepository.findByPostId(postId).orElseThrow(() -> new CustomException("λκΈ μμ±", ErrorCode.NotFound));
        Comment comment = new Comment(commentRequestDto.getComment(), post, currentMember);
        int sizeOfComment = post.getCommentSize();
        post.commentUpdate(sizeOfComment + 1);
        commentRepository.save(comment);
        return ResponseEntity.ok(CommentResponseDto.builder()
                .commentId(comment.getId())
                .name(currentMember.getName())
                .comment(comment.getComment())
                .createdAt(Chrono.timesAgo(comment.getCreatedAt()))
                .build()
        );
    }

    @Transactional
    public ResponseEntity<CommentResponseDto> deleteComment(Long commentId, Member currentMember) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException("λκΈ μ­μ ", ErrorCode.NotFound));
        Post post = comment.getPost();
        if (comment.getMember().getId().equals(currentMember.getId())) {
            int sizeOfComment = post.getCommentSize();
            post.commentUpdate(sizeOfComment - 1);
            commentRepository.deleteById(commentId);
        } else {
            throw new CustomException("λκΈ μμ±μ", ErrorCode.NotMatch);
        }
        return ResponseEntity.ok(
                CommentResponseDto.builder()
                        .commentId(commentId)
                        .build()
        );
    }

    //λλκΈ μμ±
    @Transactional
    public ResponseEntity<CommentResponseDto> createRecomment(Long commentId, CommentRequestDto commentRequestDto, Member currentMember) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException("λλκΈ μμ± κ²μκΈ", ErrorCode.NotFound));
        Recomment recomment = new Recomment(comment, currentMember, commentRequestDto.getComment());
        recommentRepository.save(recomment);
        return ResponseEntity.ok(
                CommentResponseDto.builder()
                        .commentId(recomment.getId())
                        .name(currentMember.getName())
                        .comment(recomment.getRecomment())
                        .createdAt(Chrono.timesAgo(recomment.getCreatedAt()))
                        .build()
        );
    }

    @Transactional
    public ResponseEntity<CommentResponseDto> deleteRecomment(Long recommentId, Member currentMember) {
        Recomment recomment = recommentRepository.findById(recommentId).orElseThrow(() -> new CustomException("λλκΈ μ­μ  κ²μκΈ", ErrorCode.NotFound));
        if (recomment.getMember().getId().equals(currentMember.getId())) {
            recommentRepository.delete(recomment);
        } else {
            throw new CustomException("λλκΈ μ­μ  νμ", ErrorCode.NotMatch);
        }
        return ResponseEntity.ok(
                CommentResponseDto.builder()
                        .build()
        );
    }

    //λλκΈ μ‘°ν
    @Transactional(readOnly = true)
    public ResponseEntity<List<RecommentResDto>> selectRecomment(Long commentId, Member currentMember) {
        List<Recomment> recommentList = recommentRepository.findRecommentsByCommentId(commentId);
        List<RecommentResDto> dtoList = new ArrayList<>();
        for (Recomment r : recommentList){
            boolean amILike = recommentLikeRepository.existsByRecommentIdAndMember(r.getId(), currentMember);
            dtoList.add(new RecommentResDto(r, amILike));
        }
        return ResponseEntity.ok(dtoList);
    }
}
