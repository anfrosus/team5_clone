package com.team5.sparta_clone_5.repository;

import com.team5.sparta_clone_5.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByName(String name);
}
