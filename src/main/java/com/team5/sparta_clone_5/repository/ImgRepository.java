package com.team5.sparta_clone_5.repository;

import com.team5.sparta_clone_5.model.Img;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImgRepository extends JpaRepository<Img, Long> {
    List<Img> findByPostPostId(Long postId);
    void deleteByPostPostId(Long postId);

}
