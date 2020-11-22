package com.point.api.repository;

import com.point.api.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {

    public Optional<Point> findByAccountIdAndTodoId(String accountId, String todoId);

    public List<Point> findAllByAccountIdAndCreatedBetween(String accountId, LocalDateTime start, LocalDateTime end);
}
