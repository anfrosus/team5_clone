package com.team5.sparta_clone_5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne
    @JoinColumn
    private Member member;

    @OneToMany(mappedBy = "comment")
    private List<Recomment> recomment = new ArrayList<>();

    public Comment(String comment, Post post, Member member) {
        this.comment = comment;
        this.post = post;
        this.member = member;
    }
}
