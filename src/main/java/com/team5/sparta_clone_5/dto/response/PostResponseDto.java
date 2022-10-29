package com.team5.sparta_clone_5.dto.response;

import com.team5.sparta_clone_5.model.Post;
import com.team5.sparta_clone_5.util.Chrono;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDto {
    private Long postId;
    private String name;
    private String contents;

    private String img;
    private int commentSize;
    private int likeSize;

    private String createdAt;
    private String modifiedAt;

    public PostResponseDto(Post post) {
        this.postId = post.getPostId();
        this.name = post.getMember().getName();
        this.contents = post.getContents();
        this.commentSize = post.getCommentSize();
        this.likeSize = post.getLikeSize();
        this.img = post.getImg();
        this.createdAt = Chrono.timesAgo(post.getCreatedAt());
        this.modifiedAt = Chrono.timesAgo(post.getModifiedAt());
    }
}
