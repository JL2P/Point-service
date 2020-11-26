package com.point.api.domain.service;

import com.point.api.domain.InGroupRank;

import java.util.List;

public interface InGroupRankService {
public InGroupRank sumInGroupPoint (String accountId, String groupId, int point);
public List<InGroupRank> getInGroupAllRanking (String groupId);

}
