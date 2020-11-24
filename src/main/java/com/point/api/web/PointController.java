package com.point.api.web;

import com.point.api.domain.Point;
import com.point.api.domain.Rank;
import com.point.api.domain.service.PointService;
import com.point.api.domain.service.RankService;
import com.point.api.repository.PointRepository;
import com.point.api.web.dto.PointAddDto;
import com.point.api.web.dto.PointDto;
import com.point.api.web.dto.RankAddDto;
import com.point.api.web.dto.RankDto;
import com.point.api.web.message.ErrorMessage;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"1. Point"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/points") //컨트롤러 기본 URL
public class PointController {
    private final PointService pointService;
    private final RankService rankService;

//    // PointList 정상적으로 조회 되는지 테스트
//    @GetMapping()
//    public List<PointDto> getPointTest(){
//        return pointService.getAllPoint().stream().map(point -> new PointDto(point)).collect(Collectors.toList());
//    }

//    @PostMapping("/addPoint") //현재까지 point를 기준없이 누적 카운트
//    public PointDto addPoint(@RequestBody PointAddDto pointAddDto) {
//        Point newPoint = pointAddDto.toDomain();
//
//        /*
//        * SELECT * FROM POINT
//        * WHERE accountId = account
//        * and created == 오늘날짜
//        * */
//
//        //완료된 Todo 개수 전체 카운트
//        List<Point> completeTodo = pointService.getAllPoint();
//
//        int completeTodoCnt = completeTodo.size();
//        //인정점수 max 100점, todo 개수 max 4개 (todo 개당 25점.)
//        if (completeTodoCnt > 4) {
//            completeTodoCnt = 4;
//        }
//        int todoPoint = completeTodoCnt*25;
//        int likeTotalCnt=0;
//        int likePoint=0;
//        //좋아요 개수 전체 카운트
//        for(int i=0; i<completeTodoCnt; i++) {
//            likeTotalCnt = completeTodo.get(i).getLikeCount();
//        }
//
//        if(likeTotalCnt>50) likePoint=5;
//        else if(likeTotalCnt>=1) {
//            likePoint = likeTotalCnt/10;
//        }
//        else likePoint=0;
//        //계산한 todo 포인트 + 좋아요 포인트를 합산
//        int totalPoint = todoPoint + likePoint;
//        //점수 셋팅.
//        newPoint.setPoint(totalPoint);
//
//        return new PointDto(pointService.addPoint(newPoint));
//    }


    @PostMapping("/addPoint")
    public PointDto giveApoint(@RequestBody PointAddDto pointAddDto) {
        Point point = pointAddDto.toDomain();


        String accountId = pointAddDto.getAccountId();
        int likeCount= pointAddDto.getLikeCount();
        int pointValue = 0;

        // 오늘 하루동안 완료한 갯수 가져오기
        int todayCompletedCount = pointService.getTodayCompletedCount(accountId);

        // 오늘 하루동안 완료한 갯수가 4개 미만일 경우 점수 계산
        if(todayCompletedCount < 4){

            // 좋아요를 통한 점수는 최대 5점까지
            if(likeCount >= 50) pointValue = 5;
            else {
                pointValue = likeCount/10;
            }
            //기본점수 25점
            pointValue+=25;
        }

        // 계산된 점수 세팅
        point.setPoint(pointValue);
        // 점수 생성
        PointDto pointDto = new PointDto(pointService.addPoint(point));
        rankService.sumPoint(accountId, pointValue);
        return pointDto;
    }

    //유저의 전체 점수를 전체 조회
    @GetMapping("/{accountId}")
    public List<PointDto> getUserPoint(@PathVariable String accountId) {
        System.out.println(accountId);
        List<Point> pointList = pointService.getUserAllPoint(accountId);

        return pointList.stream().map(point -> new PointDto(point)).collect(Collectors.toList());
    }

    //유저의 전체 점수를 날짜별로 조회
    @PostMapping("/date")
    public int getUserPointByDate (@RequestBody PointDto pointDto) {
        String accountId = pointDto.getAccountId();
        LocalDateTime created = pointDto.getCreated();

        List<Point> pointList =  pointService.getUserAllPointByDate(accountId, created);
        int point=0;

        for(int i=0; i<pointList.size(); i++) {
            point += pointList.get(i).getPoint();
        }

        return point;
        //근데 여기서 point 값 산정해서 리턴하는 것보다s List<Point>로 리턴해서 레파지토리에서 계산해주는게 나을거같다.
    }

    //유저 점수 부여 취소
    @DeleteMapping("/cancel")
    public void cancelPoint (@RequestBody PointDto pointDto) {
        String accountId = pointDto.getAccountId();
        String todoId = pointDto.getTodoId();

        pointService.cancelPoint(accountId, todoId);

    }

    //전체 유저 랭킹 조회
    @GetMapping("/all/ranking")
    public List<RankDto> getAllAccountRanking(){
        return rankService.getUserAllRanking().stream().map(rank-> new RankDto(rank)).collect(Collectors.toList());
    }






    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody
    ErrorMessage runTimeError(RuntimeException e) {
        ErrorMessage error = new ErrorMessage();
        error.setMessage(e.getMessage());
        return error;
    }
}
