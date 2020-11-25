package com.point.api.repository;

import com.point.api.domain.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RankRepository extends JpaRepository<Rank, Long> {
    public Optional<Rank> findByAccountId(String accountId);
}
