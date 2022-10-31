package com.team5.sparta_clone_5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(nullable = true)
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


    public Post(PostRequestDto postRequestDto, Member member, String img) {
        this.contents = postRequestDto.getContents();
        this.img = img;
        this.member = member;
    }

    public void postLikeUpdate(int size) {
        this.likeSize = size;
    }

    public void commentUpdate(int size){this.commentSize = size;}
}

