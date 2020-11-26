package com.point.api.repository;

import com.point.api.domain.GroupRank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRankRepository extends JpaRepository <GroupRank, Long> {
    public Optional<GroupRank> findByGroupId(String GroupId);
}
