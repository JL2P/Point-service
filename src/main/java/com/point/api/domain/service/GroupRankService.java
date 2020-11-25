package com.point.api.domain.service;

import com.point.api.domain.GroupRank;
import com.point.api.domain.Rank;

import java.util.List;

public interface GroupRankService {
    public GroupRank sumGroupPoint (String groupId, int point);
    public List<GroupRank> getGroupAllRanking ();
}
