package com.team5.sparta_clone_5.repository;

import com.team5.sparta_clone_5.model.Member;
import com.team5.sparta_clone_5.model.RecommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommentLikeRepository extends JpaRepository<RecommentLike, Long> {
    boolean existsByRecommentIdAndMember(Long recommentId, Member member);
    void deleteByRecommentIdAndMember(Long recommentId, Member member);
}
