package com.point.api.web.dto;

import com.point.api.domain.Point;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class PointDto {
    private Long pointId;
    private String accountId; // 유저 아이디
    private String todoId;    // 완료한 TodoId
    private int likeCount;   // 완료를 누른 시점의 좋아요 갯수
    private int point;    // 계획을 완료함으로써 얻는 총 포인트
    private LocalDateTime created;

    public PointDto(Point point){
        this.pointId=point.getId();
        this.accountId=point.getAccountId();
        this.todoId=point.getTodoId();
        this.likeCount=point.getLikeCount();
        this.point=point.getPoint();
        this.created=point.getCreated();
    }
}
