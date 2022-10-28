package com.team5.sparta_clone_5.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Recomment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Comment comment;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Member member;

    private String recomment;

    public Recomment(Comment comment, Member member, String recomment) {
        this.comment = comment;
        this.member = member;
        this.recomment = recomment;
    }
}
