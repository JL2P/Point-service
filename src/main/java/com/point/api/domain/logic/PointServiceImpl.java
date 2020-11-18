package com.point.api.domain.logic;

import com.point.api.domain.Point;
import com.point.api.domain.service.PointService;
import com.point.api.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PointServiceImpl implements PointService {
    private final PointRepository pointRepository;

    @Override
    public List<Point> getAllPoint() {
        return pointRepository.findAll();
    }

    @Override
    public Point addPoint(Point point) {
        return pointRepository.save(point);
    }
}
