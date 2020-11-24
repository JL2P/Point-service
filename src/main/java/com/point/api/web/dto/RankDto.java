package com.point.api.web.dto;

import com.point.api.domain.Point;
import com.point.api.domain.Rank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class RankDto {
    private Long rankId;
    private String accountId; // 유저 아이디
    private int total;    // 계획을 완료함으로써 얻는 총 포인트


    public RankDto(Rank rank) {
        this.rankId = rank.getId();
        this.accountId = rank.getAccountId();
        this.total = rank.getTotal();

    }
}
