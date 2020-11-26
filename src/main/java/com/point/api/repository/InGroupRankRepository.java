package com.point.api.repository;

import com.point.api.domain.InGroupRank;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface InGroupRankRepository extends JpaRepository<InGroupRank, Long> {
    public Optional<InGroupRank> findByAccountIdAndGroupId(String AccountId, String GroupId);
    public List<InGroupRank> findByGroupIdOrderByInGroupTotalDesc(String groupId);
}

