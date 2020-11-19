package com.point.api.web;

import com.point.api.domain.Point;
import com.point.api.domain.service.PointService;
import com.point.api.repository.PointRepository;
import com.point.api.web.dto.PointAddDto;
import com.point.api.web.dto.PointDto;
import com.point.api.web.message.ErrorMessage;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"1. Point"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/points") //컨트롤러 기본 URL
public class PointController {
    private final PointService pointService;


    // PointList 정상적으로 조회 되는지 테스트
    @GetMapping()
    public List<PointDto> getPointTest(){
        return pointService.getAllPoint().stream().map(point -> new PointDto(point)).collect(Collectors.toList());
    }

    @PostMapping("/addPoint") //현재까지 point를 기준없이 누적 카운트
    public PointDto addPoint(@RequestBody PointAddDto pointAddDto) {
        Point newPoint = pointAddDto.toDomain();

        //완료된 Todo 개수 전체 카운트
        List<Point> completeTodo = pointService.getAllPoint();

        int completeTodoCnt = completeTodo.size();
        //인정점수 max 100점, todo 개수 max 4개 (todo 개당 25점.)
        if (completeTodoCnt > 4) {
            completeTodoCnt = 4;
        }
        int todoPoint = completeTodoCnt*25;
        int likeTotalCnt=0;
        int likePoint=0;
        //좋아요 개수 전체 카운트
        for(int i=0; i<completeTodoCnt; i++) {
            likeTotalCnt = completeTodo.get(i).getLikeCount();
        }

        if(likeTotalCnt>50) likePoint=5;
        else if(likeTotalCnt>=1) {
            likePoint = likeTotalCnt/10;
        }
        else likePoint=0;
        //계산한 todo 포인트 + 좋아요 포인트를 합산
        int totalPoint = todoPoint + likePoint;
        //점수 셋팅.
        newPoint.setPoint(totalPoint);

        return new PointDto(pointService.addPoint(newPoint));
    }



    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody
    ErrorMessage runTimeError(RuntimeException e) {
        ErrorMessage error = new ErrorMessage();
        error.setMessage(e.getMessage());
        return error;
    }
}
