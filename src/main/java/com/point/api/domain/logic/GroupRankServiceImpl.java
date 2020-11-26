package com.point.api.domain.logic;


import com.point.api.domain.GroupRank;
import com.point.api.domain.Rank;
import com.point.api.domain.service.GroupRankService;
import com.point.api.repository.GroupRankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GroupRankServiceImpl implements GroupRankService {
    private final GroupRankRepository groupRankRepository;

    @Override
    public GroupRank sumGroupPoint (String groupId, int point) {
        GroupRank myGroupRank = groupRankRepository.findByGroupId(groupId).orElse(GroupRank.builder().groupId(groupId).build());
        myGroupRank.setGroupTotal(myGroupRank.getGroupTotal()+point);

        return groupRankRepository.save(myGroupRank);
    }

    //전체 유저 랭킹 리스트 조회
    @Override
    public List<GroupRank> getGroupAllRanking () {
        return groupRankRepository.findAll(sortByGroupTotalDesc());
    }


    private Sort sortByGroupTotalDesc() {
        return Sort.by(Sort.Direction.DESC, "groupTotal");
    }




}
