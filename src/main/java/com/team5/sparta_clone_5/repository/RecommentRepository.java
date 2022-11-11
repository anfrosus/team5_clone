package com.team5.sparta_clone_5.repository;

import com.team5.sparta_clone_5.model.Member;
import com.team5.sparta_clone_5.model.Recomment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommentRepository extends JpaRepository<Recomment, Long> {
    List<Recomment> findRecommentsByCommentId(Long commentId);
    boolean existsByRecommentAndMember(Recomment recomment, Member member);
}
