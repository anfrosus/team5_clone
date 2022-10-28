package com.team5.sparta_clone_5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team5.sparta_clone_5.dto.request.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String img;

    @Column(nullable = false)
    private int commentSize;

    @Column(nullable = false)
    private int likeSize;

    @JsonIgnore
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    List<PostLike> postLikeList = new ArrayList<>();

    public Post(PostRequestDto postRequestDto, Member member, String img){
        this.email = member.getEmail();
        this.name = postRequestDto.getName();
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
        this.img = img;
    }

    public void postLikeUpdate(int size) {
        this.likeSize = size;
    }

}
