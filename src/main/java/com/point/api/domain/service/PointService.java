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

    //유저의 모든 점수를 전체 조회
    public List<Point> getUserAllPoint(String accountId);
    //유저의 모든 점수를 날짜별로 조회
    public List<Point> getUserAllPointByDate(String accountId, LocalDateTime created);

    //요청한 날짜 사이의 모든 점수
    public List<Point> allListsWithinThePeriod(String accountId, LocalDateTime startDatetime, LocalDateTime endDatetime);

    //전체 유저 랭킹 조회
    public List<Point> getUserAllRanking ();


    //점수 부여 취소
    public void cancelPoint (String accountId, String todoId);

   //특정 포인트 찾아오기
   public Point getPoint (String accountId, String todoId);


}
