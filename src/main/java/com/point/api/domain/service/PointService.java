package com.point.api.domain.service;

import com.point.api.domain.Point;

import java.util.List;

public interface PointService {

    public List<Point> getAllPoint();

    public Point addPoint(Point point);
}
