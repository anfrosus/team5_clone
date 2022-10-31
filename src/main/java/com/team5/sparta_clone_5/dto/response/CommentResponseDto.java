package com.team5.sparta_clone_5.dto.response;

import com.team5.sparta_clone_5.model.Comment;
import com.team5.sparta_clone_5.util.Chrono;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {
    private Long commentId;
    private String name;
    private String comment;
    private String createdAt;
    private int likeSize;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.name = comment.getMember().getName();
        this.comment = comment.getComment();
        this.createdAt = Chrono.timesAgo(comment.getCreatedAt());
        this.likeSize = comment.getCommentLikeSize();
    }
}
