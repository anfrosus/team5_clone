package com.team5.sparta_clone_5.dto.response;

import com.team5.sparta_clone_5.model.Img;
import com.team5.sparta_clone_5.model.Post;
import com.team5.sparta_clone_5.util.Chrono;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor

public class OnePostResponseDto {
    private Long postId;
    private String name;
    private String contents;

    private String img;
    private int commentSize;
    private int likeSize;
    private String createdAt;
    private String modifiedAt;
    private Boolean amILike;

    private List<CommentResponseDto> commentList;

    public OnePostResponseDto(Post post) {
        this.postId = post.getPostId();
        this.name = post.getMember().getName();
        this.contents = post.getContents();
        this.commentSize = post.getCommentSize();
        this.likeSize = post.getLikeSize();
        this.createdAt = Chrono.timesAgo(post.getCreatedAt());
        this.modifiedAt = Chrono.timesAgo(post.getModifiedAt());
    }

    public OnePostResponseDto(Post post, List<String> img, List<CommentResponseDto> commentResponseDtoList, Boolean amILike) {
        this.postId = post.getPostId();
        this.name = post.getMember().getName();
        this.contents = post.getContents();
        this.commentSize = post.getCommentSize();
        this.likeSize = post.getLikeSize();
        this.createdAt = Chrono.timesAgo(post.getCreatedAt());
        this.modifiedAt = Chrono.timesAgo(post.getModifiedAt());
        this.commentList = commentResponseDtoList;
        this.amILike = amILike;
        this.img = img.get(0);
    }
}
