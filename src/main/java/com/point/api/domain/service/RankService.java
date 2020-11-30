package com.point.api.domain.service;

import com.point.api.domain.Point;
import com.point.api.domain.Rank;

import java.util.List;

public interface RankService {

    public Rank sumPoint(String accountId, int point);
    public List<Rank> getUserAllRanking ();
    public Rank getMyRank(String accountId);
}
