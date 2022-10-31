package com.team5.sparta_clone_5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.team5.sparta_clone_5.dto.request.PostRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Member member;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private int commentSize;

    @Column(nullable = false)
    private int likeSize;

    @Column(nullable = false)
    private String img;

//    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//    List<Img> img = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    List<PostLike> postLikeList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Img> imgs;



    public Post(String postRequestDto, Member member) {
        this.contents = postRequestDto;
        this.member = member;

    }

    public Post(String img) {
        this.img = img;

    }

    public void postLikeUpdate(int size) {
        this.likeSize = size;
    }

    public void commentUpdate(int size){this.commentSize = size;}
}

