package com.point.api.web.dto;

import com.point.api.domain.InGroupRank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InGroupRankDto {

    private long inGroupRankId;
    private String accountId;
    private String groupId;
    private int inGroupTotal;

    public InGroupRankDto (InGroupRank inGroupRank) {
        this.inGroupRankId = inGroupRank.getId();
        this.accountId = inGroupRank.getAccountId();
        this.groupId = inGroupRank.getGroupId();
        this.inGroupTotal = inGroupRank.getInGroupTotal();
    }
}
