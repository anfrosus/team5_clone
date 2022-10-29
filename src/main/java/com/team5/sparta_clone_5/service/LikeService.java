package com.team5.sparta_clone_5.service;

import com.team5.sparta_clone_5.exception.CustomException;
import com.team5.sparta_clone_5.exception.ErrorCode;
import com.team5.sparta_clone_5.model.*;
import com.team5.sparta_clone_5.repository.CommentLikeRepository;
import com.team5.sparta_clone_5.repository.CommentRepository;
import com.team5.sparta_clone_5.repository.PostLikeRepository;
import com.team5.sparta_clone_5.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public String postLike(Long postId, Member currentMember) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new CustomException("게시글", ErrorCode.NotFound));
        int sizeOfLikes = post.getLikeSize();
        String isLike = "";
        if (!postLikeRepository.existsByMemberAndPost(currentMember, post)){
            PostLike postLike = new PostLike(post, currentMember);
            post.postLikeUpdate(sizeOfLikes + 1);
            postLikeRepository.save(postLike);
            isLike = "좋아요 완료";
        }else{
            postLikeRepository.deleteByMemberAndPost(currentMember, post);
            post.postLikeUpdate(sizeOfLikes - 1);
            isLike = "좋아요 취소";
        }
        return isLike;
    }

    @Transactional
    public String commentLike(Long commentId, Member currentMember) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException("댓글 좋아요", ErrorCode.NotFound));
        int sizeOfCoLikes = comment.getCommentLikeSize();
        String isLike = "";
        if(!commentLikeRepository.existsByCommentIdAndMember(commentId, currentMember)){
            CommentLike commentLike = new CommentLike(comment, currentMember);
            comment.updateLikeSize(sizeOfCoLikes + 1);
            commentLikeRepository.save(commentLike);
            isLike = "좋아요 완료";
        }else{
            commentLikeRepository.deleteByCommentIdAndMember(commentId, currentMember);
            comment.updateLikeSize(sizeOfCoLikes - 1);
            isLike = "좋아요 취소";
        }
        return isLike;
    }
}
