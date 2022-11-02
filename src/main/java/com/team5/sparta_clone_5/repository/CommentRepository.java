package com.team5.sparta_clone_5.repository;

import com.team5.sparta_clone_5.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
