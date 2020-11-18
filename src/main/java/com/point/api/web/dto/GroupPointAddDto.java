package com.point.api.web.dto;

import com.point.api.domain.GroupPoint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class GroupPointAddDto {
    private String accountId; // 유저 아이디
    private String todoId;    // 완료한 TodoId
    private String groupId;    // 완료한 TodoId
    private int likeCount;   // 완료를 누른 시점의 좋아요 갯수

    public GroupPoint toDomain(){
        return GroupPoint.builder()
                .accountId(this.accountId)
                .todoId(this.todoId)
                .groupId(this.groupId)
                .likeCount(this.likeCount)
                .build();
    }
}
