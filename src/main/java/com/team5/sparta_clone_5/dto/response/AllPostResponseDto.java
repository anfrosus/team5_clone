package com.team5.sparta_clone_5.dto.response;

import com.team5.sparta_clone_5.model.Img;
import com.team5.sparta_clone_5.model.Post;
import com.team5.sparta_clone_5.util.Chrono;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AllPostResponseDto {
    private Long postId;
    private String name;
    private String contents;

    private List<String> imgs;
    private int commentSize;
    private int likeSize;

    private String createdAt;
    private String modifiedAt;


    public AllPostResponseDto(Post post,List<String> imgs) {
        this.postId = post.getPostId();
        this.name = post.getMember().getName();
        this.contents = post.getContents();
        this.imgs = imgs;
        this.commentSize = post.getCommentSize();
        this.likeSize = post.getLikeSize();
        this.createdAt = Chrono.timesAgo(post.getCreatedAt());
        this.modifiedAt = Chrono.timesAgo(post.getModifiedAt());
    }
}
