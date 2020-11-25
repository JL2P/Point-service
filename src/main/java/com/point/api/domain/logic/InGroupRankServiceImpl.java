package com.point.api.domain.logic;


import com.point.api.domain.GroupRank;
import com.point.api.domain.InGroupRank;
import com.point.api.domain.service.InGroupRankService;
import com.point.api.repository.InGroupRankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InGroupRankServiceImpl implements InGroupRankService {
    private final InGroupRankRepository inGroupRankRepository;

    @Override
    public InGroupRank sumInGroupPoint (String accountId, String groupId, int point) {
        InGroupRank inMyGroupRank = inGroupRankRepository.findByAccountIdAndGroupId(accountId, groupId)
                .orElse(InGroupRank.builder().accountId(accountId).groupId(groupId).build());
            inMyGroupRank.setInGroupTotal(inMyGroupRank.getInGroupTotal()+point);

        return inGroupRankRepository.save(inMyGroupRank);
    }

    // 특정 그룹 내에서 유저 랭킹 리스트 조회
    @Override
    public List<InGroupRank> getInGroupAllRanking (String groupId) {
        return inGroupRankRepository.findByGroupIdOrderByInGroupTotalDesc(groupId);
    }


//    private Sort sortByInGroupTotalDesc() {
//        return Sort.by(Sort.Direction.DESC, "inGroupTotal");
//    }

}
