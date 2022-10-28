package com.team5.sparta_clone_5.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Post> postList;
//
//    @OneToMany(mappedBy = "member")
//    private List<Comment> commentList;
//
//    @OneToMany(mappedBy = "member")
//    private List<PostLike> postLikeList;
}
