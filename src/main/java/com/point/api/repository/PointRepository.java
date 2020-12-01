package com.point.api.repository;

import com.point.api.domain.Point;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {

    public Optional<Point> findByAccountIdAndTodoId(String accountId, String todoId);

    public List<Point> findAllByAccountIdAndCreatedBetween(String accountId, LocalDateTime start, LocalDateTime end);
    public List<Point> findByAccountId(String accountId);
    List<Point> findAllWithCustomOrderBy(Sort sort);
    List<Point> findAllByPoint (int point, Pageable pageable); //PagindAndSortingRepository를 확장한 JpaRepository 사용해도 된다.
    public Optional<Point> findByTodoId(String todoId);
}
