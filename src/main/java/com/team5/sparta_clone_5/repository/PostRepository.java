package com.team5.sparta_clone_5.repository;

import com.team5.sparta_clone_5.model.Member;
import com.team5.sparta_clone_5.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    Optional<Post> findByPostId(Long postId);
    Post findPostByPostIdAndMember(Long postId,Member member);

    Optional<Post> findByMember(Member member);
}
