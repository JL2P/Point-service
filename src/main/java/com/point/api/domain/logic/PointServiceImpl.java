package com.point.api.domain.logic;

import com.point.api.domain.Point;
import com.point.api.domain.service.PointService;
import com.point.api.exception.PointExistException;
import com.point.api.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public Point addPoint(Point point) throws PointExistException {
        //이미 반영된 점수가 있을경우 점수를 부여하면 안된다.
        if(!pointRepository.findByAccountIdAndTodoId(point.getAccountId(), point.getTodoId()).isEmpty()){
            throw new PointExistException("이미 점수가 반영되었습니다.");
        }

        return pointRepository.save(point);
    }

    @Override
    public int getTodayCompletedCount(String accountId) {
        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0,0,0));
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));
        //오늘 내가 Todo를 완료하며 받은 점수가 몇개 인지 가져온다.
        return pointRepository.findAllByAccountIdAndCreatedBetween(accountId, startDatetime, endDatetime).size();
    }
}
