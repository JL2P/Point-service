package com.point.api.web.dto;

import com.point.api.domain.Rank;

public class RankAddDto {
    private String accountId; // 유저 아이디
    private String todoId;
    private int point;

    public Rank toDomain() {
        return Rank.builder()
                .accountId(this.accountId)
                .total(this.point)
                .build();
    }
}
