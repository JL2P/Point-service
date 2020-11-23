package com.point.api.domain.service;

import com.point.api.domain.Point;
import com.point.api.exception.PointExistException;

import java.time.LocalDateTime;
import java.util.List;

public interface PointService {

    public List<Point> getAllPoint();

    //포인트를 추가한다
    public Point addPoint(Point point) throws PointExistException;

    //오늘 완료한 계획의 갯수를 계산한다.
    public int getTodayCompletedCount(String accountId);

    public List<Point> getUserAllPointByDate(String accountId, LocalDateTime created);

    //점수 부여 취소
    public void cancelPoint (String accountId, String todoId);
}
