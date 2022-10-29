package com.team5.sparta_clone_5.repository;

import com.team5.sparta_clone_5.model.CommentLike;
import com.team5.sparta_clone_5.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByCommentIdAndMember(Long commentId, Member member);
    void deleteByCommentIdAndMember(Long commentId, Member member);
}
