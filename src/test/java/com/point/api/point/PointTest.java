package com.point.api.point;

import com.point.api.domain.Point;
import com.point.api.repository.PointRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
public class PointTest {


    @Autowired
    private PointRepository pointRepository;

    @Before
    public void init(){
        String accountId = "test";
        Point newPoint1 = Point.builder().accountId(accountId).todoId("1").likeCount(43).build();
        Point newPoint2 = Point.builder().accountId(accountId).todoId("2").likeCount(23).build();
        Point newPoint3 = Point.builder().accountId(accountId).todoId("3").likeCount(12).build();
        Point newPoint4 = Point.builder().accountId(accountId).todoId("4").likeCount(34).build();
        Point newPoint5 = Point.builder().accountId(accountId).todoId("5").likeCount(235).build();

        pointRepository.save(newPoint1);
        pointRepository.save(newPoint2);
        pointRepository.save(newPoint3);
        pointRepository.save(newPoint4);
        pointRepository.save(newPoint5);
    }

    @Test
    public void 오늘_완료한_계획의_갯수_TEST(){
        String accountId = "test";

        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0));
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));
//        System.out.println(startDatetime);
//        System.out.println(endDatetime);

        List<Point> points = pointRepository.findAllByAccountIdAndCreatedBetween(accountId, startDatetime, endDatetime);

//        for(int i=0; i<points.size(); i++){
//            System.out.println(points.get(0).getCreated());
//        }

        int todayCompletedCount = points.size();
//        System.out.println(todayCompletedCount);

        assertThat(todayCompletedCount).isEqualTo(5);
    }
}
