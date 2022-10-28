package com.team5.sparta_clone_5.dto.response;

import com.team5.sparta_clone_5.model.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private Long postId;
    private String name;
    private String contents;
    private String img;
    private int commentSize;
    private int likeSize;

    public PostResponseDto(Post post) {
        this.postId = post.getPostId();
        this.name = post.getMember().getName();
        this.contents = post.getContents();
        this.commentSize = commentSize;
        this.likeSize = likeSize;
    }
}
