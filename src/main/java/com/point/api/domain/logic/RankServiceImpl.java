package com.point.api.domain.logic;

import com.point.api.domain.Point;
import com.point.api.domain.Rank;
import com.point.api.domain.service.RankService;
import com.point.api.repository.RankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RankServiceImpl implements RankService {
    private final RankRepository rankRepository;

    @Override
    public Rank sumPoint(String accountId, int point) {
        Rank myRank = rankRepository.findByAccountId(accountId).orElse(Rank.builder().accountId(accountId).build());
        myRank.setTotal(myRank.getTotal()+point);
        return rankRepository.save(myRank);
    }


    //전체 유저 랭킹 리스트 조회
    @Override
    public List<Rank> getUserAllRanking () {
       return rankRepository.findAll(sortByTotalDesc());
    }


    private Sort sortByTotalDesc() {
        return Sort.by(Sort.Direction.DESC, "total");
    }
}
