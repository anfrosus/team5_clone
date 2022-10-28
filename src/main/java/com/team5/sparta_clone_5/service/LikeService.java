package com.team5.sparta_clone_5.service;

import com.team5.sparta_clone_5.dto.response.GlobalResDto;
import com.team5.sparta_clone_5.dto.response.PostResponseDto;
import com.team5.sparta_clone_5.exception.CustomException;
import com.team5.sparta_clone_5.exception.ErrorCode;
import com.team5.sparta_clone_5.model.Member;
import com.team5.sparta_clone_5.model.Post;
import com.team5.sparta_clone_5.model.PostLike;
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
}
