package com.point.api.web.dto;

import com.point.api.domain.GroupPoint;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GroupPointDto {
    private Long groupPointId;
    private String accountId; // 유저 아이디
    private String todoId;    // 완료한 TodoId
    private String groupId;   // GroupTodo의 GroupId
    private int likeCount;   // 완료를 누른 시점의 좋아요 갯수
    private int point;    // 계획을 완료함으로써 얻는 총 포인트

    public GroupPointDto(GroupPoint groupPoint){
        this.groupPointId = groupPoint.getId();
        this.accountId = groupPoint.getAccountId();
        this.todoId = groupPoint.getAccountId();
        this.groupId = groupPoint.getGroupId();
        this.likeCount = groupPoint.getLikeCount();
        this.point = groupPoint.getPoint();
    }
}
