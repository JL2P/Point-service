package com.point.api.web.dto;

import com.point.api.domain.GroupRank;
import com.point.api.domain.Rank;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class GroupRankDto {

    private Long groupRankId;
    private String groupId;
    private String accountId; // 유저 아이디
    private int groupTotal;    // 계획을 완료함으로써 얻는 총 포인트


    public GroupRankDto(GroupRank groupRank) {
       this.groupRankId = groupRank.getId();
       this.groupId = groupRank.getGroupId();
       this.accountId = groupRank.getAccountId();
       this.groupTotal = groupRank.getGroupTotal();

    }
}
