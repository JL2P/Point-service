package com.point.api.domain.logic;

import com.point.api.domain.Point;
import com.point.api.domain.service.PointService;
import com.point.api.exception.PointExistException;
import com.point.api.repository.PointRepository;
import com.point.api.repository.RankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PointServiceImpl implements PointService {
    private final PointRepository pointRepository;

//    Pageable sortedByPointAsc = PageRequest.of(0,30, Sort.by("point").ascending());


    @Override
    public List<Point> getAllPoint() {
        return pointRepository.findAll();
    }

    @Override
    public Point addPoint(Point point) throws PointExistException {
        //이미 반영된 점수가 있을경우 점수를 부여하면 안된다.
        if (!pointRepository.findByAccountIdAndTodoId(point.getAccountId(), point.getTodoId()).isEmpty()) {
            throw new PointExistException("이미 점수가 반영되었습니다.");
        }
        return pointRepository.save(point);
    }

    @Override
    public int getTodayCompletedCount(String accountId) {
        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0, 0, 0));
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));
        //오늘 내가 Todo를 완료하며 받은 점수가 몇개 인지 가져온다.
        return pointRepository.findAllByAccountIdAndCreatedBetween(accountId, startDatetime, endDatetime).size();
    }

    //유저의 모든 점수 조회(리턴받은 리스트로 누적점수 계산은 프론트에서 함.)
    @Override
    public List<Point> getUserAllPoint (String accountId) {
        List<Point> pointList = pointRepository.findByAccountId(accountId);

        return pointList;
    }

    //특정 날짜 유저의 모든 점수 조회
    @Override
    public List<Point> getUserAllPointByDate(String accountId, LocalDateTime created) {
        LocalDateTime startDatetime = created.of(created.toLocalDate(), LocalTime.of(0, 0, 0));
        LocalDateTime endDatetime = created.of(created.toLocalDate(), LocalTime.of(23, 59, 59));
        List<Point> pointList = pointRepository.findAllByAccountIdAndCreatedBetween(accountId, startDatetime, endDatetime);

        return pointList;
    }


    //요청한 날짜 사이의 모든 점수
    @Override
    public List<Point> allListsWithinThePeriod(String accountId, LocalDateTime start, LocalDateTime end){
        LocalDateTime startDatetime = start.of(start.toLocalDate(), LocalTime.of(0, 0, 0));
        LocalDateTime endDatetime = end.of(end.toLocalDate(), LocalTime.of(23, 59, 59));
        List<Point> pointList = pointRepository.findAllByAccountIdAndCreatedBetween(accountId, startDatetime, endDatetime);
        return pointList;
    }



    //전체 유저 랭킹 조회
    @Override
    public List<Point> getUserAllRanking () {
        List<Point> pointList = pointRepository.findAllWithCustomOrderBy(Sort.by(Sort.Direction.DESC, "point"));
        return pointList;
    }

    //Pageable sortedByPointAsc = PageRequest.of(0,30, Sort.by("point").ascending());





    //점수 부여 취소
    @Override
    public void cancelPoint(String accountId, String todoId) {
        Point deletePoint = pointRepository.findByAccountIdAndTodoId(accountId, todoId).orElseThrow(()->new NoSuchElementException());
        pointRepository.delete(deletePoint);
    }
    //특정 포인트 불러오기
    @Override
    public Point getPoint (String accountId, String todoId) {
        return pointRepository.findByAccountIdAndTodoId(accountId, todoId).orElseThrow(()->new NoSuchElementException());
    }
}