package com.point.api.domain.logic;

import com.point.api.domain.GroupPoint;
import com.point.api.domain.Point;
import com.point.api.domain.service.GroupPointService;
import com.point.api.exception.PointExistException;
import com.point.api.repository.GroupPointRepository;
import com.point.api.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class GroupPointServiceImpl implements GroupPointService {
    private final GroupPointRepository groupPointRepository;
    private final PointRepository pointRepository;

    @Override
    public List<GroupPoint> getAllGroupPoint() {
        return groupPointRepository.findAll();
    }

    @Override
    public GroupPoint addGroupPoint(GroupPoint groupPoint) {
        //이미 반영된 점수가 있을경우 점수를 부여하면 안된다.
        if (!groupPointRepository.findByAccountIdAndGroupIdAndTodoId(groupPoint.getAccountId(),groupPoint.getGroupId(), groupPoint.getTodoId()).isEmpty()) {
            throw new PointExistException("이미 점수가 반영되었습니다.");
        }
        return groupPointRepository.save(groupPoint);
    }
    //
    @Override
    public int getTodayCompletedCount(String accountId, String groupId) {
        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0, 0, 0));
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));
        //오늘 그룹 Todo를 완료하며 받은 점수가 몇개 인지 가져온다.
        return groupPointRepository.findAllByAccountIdAndGroupIdAndCreatedBetween(accountId, groupId, startDatetime, endDatetime).size();

    }

    //그룹의 모든 점수 조회 (리턴받은 리스트로 누적점수 계산은 프론트에서 함)
    @Override
    public List<GroupPoint> getGroupAllPoint(String groupId) {
        List<GroupPoint> pointList = groupPointRepository.findByGroupId(groupId);
        return pointList;
    }

    //그룹점수 삭제
    @Override
    public void deleteGroupPoint (String accountId, String groupId, String todoId) {
        GroupPoint groupPoint = groupPointRepository.findByAccountIdAndGroupIdAndTodoId(accountId, groupId, todoId)
                .orElseThrow( () -> new NoSuchElementException() );
        Point point = pointRepository.findByAccountIdAndTodoId(accountId, todoId).orElseThrow( ()->new NoSuchElementException());

        groupPointRepository.delete(groupPoint);
        pointRepository.delete(point);
    }
}