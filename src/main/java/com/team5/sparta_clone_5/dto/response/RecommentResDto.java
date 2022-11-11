package com.team5.sparta_clone_5.dto.response;

import com.team5.sparta_clone_5.model.Comment;
import com.team5.sparta_clone_5.model.Recomment;
import com.team5.sparta_clone_5.util.Chrono;
import lombok.Getter;

@Getter
public class RecommentResDto {
    private Long recommentId;
    private String name;
    private String recomment;
    private String createdAt;
    private int likeSize;

    private boolean amILike;


    public RecommentResDto(Recomment recomment, boolean amILike) {
        this.recommentId = recomment.getId();
        this.name = recomment.getMember().getName();
        this.recomment = recomment.getRecomment();
        this.createdAt = Chrono.timesAgo(recomment.getCreatedAt());
        this.likeSize = recomment.getRecommentLikeSize();
        this.amILike = amILike;
    }
}
