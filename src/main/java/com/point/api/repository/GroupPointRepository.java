package com.point.api.repository;

import com.point.api.domain.GroupPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GroupPointRepository extends JpaRepository<GroupPoint, Long> {
    public Optional<GroupPoint> findByAccountIdAndTodoId(String accountId, String todoId);
    public List<GroupPoint> findAllByGroupIdAndCreatedBetween(String accountId, LocalDateTime start, LocalDateTime end);
    public List<GroupPoint> findByGroupId(String groupId);
}
