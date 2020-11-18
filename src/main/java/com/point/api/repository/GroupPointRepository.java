package com.point.api.repository;

import com.point.api.domain.GroupPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupPointRepository extends JpaRepository<GroupPoint, Long> {
}
