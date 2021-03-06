package com.point.api.web;

import com.point.api.domain.Point;
import com.point.api.domain.Rank;
import com.point.api.domain.service.PointService;
import com.point.api.domain.service.RankService;
import com.point.api.repository.PointRepository;
import com.point.api.web.dto.*;
import com.point.api.web.message.ErrorMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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


    @ApiOperation(value = "개인 점수 추가, 개인 점수 누적", notes = "점수 부여 기준에 맞게 산정된 점수를 point 테이블에 추가, 기존점수+산정된 점수 ranks 테이블에 추가")
    @PostMapping("/addPoint")
    public PointDto giveApoint(@RequestBody PointAddDto pointAddDto) {
        Point point = pointAddDto.toDomain();

        String accountId = pointAddDto.getAccountId();
        int likeCount = pointAddDto.getLikeCount();
        int pointValue = 0;

        // 오늘 하루동안 완료한 갯수 가져오기
        int todayCompletedCount = pointService.getTodayCompletedCount(accountId);

        // 오늘 하루동안 완료한 갯수가 4개 미만일 경우 점수 계산
        if (todayCompletedCount < 4) {

            // 좋아요를 통한 점수는 최대 5점까지
            if (likeCount >= 50) pointValue = 5;
            else {
                pointValue = likeCount / 10;
            }
            //기본점수 25점
            pointValue += 25;
        }

        // 계산된 점수 세팅
        point.setPoint(pointValue);
        // 점수 생성
        PointDto pointDto = new PointDto(pointService.addPoint(point));
        rankService.sumPoint(accountId, pointValue);
        return pointDto;
    }


    //유저의 전체 점수 이력을 조회
    @ApiOperation(value = "전체 개인 점수 이력 조회", notes = "유저의 전체 개인 점수 이력을 조회")
    @GetMapping("/pointList/{accountId}")
    public List<PointDto> getUserAllPointList(@PathVariable String accountId) {
        List<Point> pointList = pointService.getUserAllPoint(accountId);
        return pointList.stream().map(point -> new PointDto(point)).collect(Collectors.toList());
    }

    //유저의 전체 누적 점수를 조회
    @ApiOperation(value = "전체 개인 누적 점수 조회 ", notes = "유저의 전체 개인 점수 이력에서 모든 점수 합산")
    @GetMapping("/{accountId}")
    public int getUserAllPoint(@PathVariable String accountId) {
        System.out.println(accountId);
        List<Point> pointList = pointService.getUserAllPoint(accountId);

//        return pointList.stream().map(point -> new PointDto(point)).collect(Collectors.toList());
        int total = 0;
        for (int i = 0; i < pointList.size(); i++) {
            total += pointList.get(i).getPoint();
        }
        return total;
    }


    @ApiOperation(value = "현재 유저 누적 점수를 조회")
    @PostMapping("/date")
    public int getUserPointByDate(@RequestBody PointDto pointDto) {
        String accountId = pointDto.getAccountId();
        LocalDateTime created = pointDto.getCreated();

        List<Point> pointList = pointService.getUserAllPointByDate(accountId, created);
        int point = 0;

        for (int i = 0; i < pointList.size(); i++) {
            point += pointList.get(i).getPoint();
        }

        return point;

    }

//    @ApiOperation(value = "유저의 누적 점수 조회")
//    @GetMapping("/{accountId}")
//    public int getUserAllPoint(@PathVariable String accountId) {
//        System.out.println(accountId);
//        List<Point> pointList = pointService.getUserAllPoint(accountId);
//
////        return pointList.stream().map(point -> new PointDto(point)).collect(Collectors.toList());
//        int total=0;
//        for(int i=0; i<pointList.size(); i++) {
//            total += pointList.get(i).getPoint();
//        }
//        return total;
//    }

    //특정날짜의 유저 누적 점수를 조회
    //------
//    @ApiOperation(value = "유저의 오늘 점수 조회")
//    @PostMapping("/date")
//    public int getUserPointByDate (@RequestBody PointDto pointDto) {
//        String accountId = pointDto.getAccountId();
//        LocalDateTime created = pointDto.getCreated();
//
//        List<Point> pointList =  pointService.getUserAllPointByDate(accountId, created);
//        int point=0;
//
//        for(int i=0; i<pointList.size(); i++) {
//            point += pointList.get(i).getPoint();
//        }
//
//        return point;
//
//    }


    // 유저의 특정 날짜 사이의 점수
    /*
     * {
     *   String accountId;
     *   LocalDateTime startTime;
     *   LocalDateTime endTime;
     * }
     */

    @ApiOperation(value = "특정 날짜의 유저 총 점수 조회")
    @PostMapping("/dateterm")
    public int getTimeTermAllPoint(@RequestBody TimeTermDto timeTermDto) {
        String accountId = timeTermDto.getAccountId();
        LocalDateTime startTime = timeTermDto.getStartTime();
        LocalDateTime endTime = timeTermDto.getEndTime();

        List<Point> pointList = pointService.allListsWithinThePeriod(accountId, startTime, endTime);
        int point = 0;

        for (int i = 0; i < pointList.size(); i++) {
            point += pointList.get(i).getPoint();
        }

        return point;
    }

//    @PostMapping("/dateterm")
//    @ApiOperation(value = "특정 기간 동안 유저의 점수 조회")
//    public int getTimeTermAllPoint(@RequestBody TimeTermDto timeTermDto){
//        String accountId = timeTermDto.getAccountId();
//        LocalDateTime startTime = timeTermDto.getStartTime();
//        LocalDateTime endTime = timeTermDto.getEndTime();
//
//        List<Point> pointList =  pointService.allListsWithinThePeriod(accountId, startTime, endTime);
//        int point=0;
//
//        for(int i=0; i<pointList.size(); i++) {
//            point += pointList.get(i).getPoint();
//        }
//
//        return point;
//    }


    //유저 점수 부여 취소

    @ApiOperation(value = "유저 점수 부여 삭제", notes = "accountId와 todoId에 해당되는 점수 취소")
    @DeleteMapping("/cancel/{accountId}/{todoId}")
    public void cancelPoint(@PathVariable String accountId, @PathVariable String todoId) {
        Point point = pointService.getPoint(accountId, todoId);
        Rank myRank = rankService.getMyRank(accountId);
        System.out.println(point.getPoint());

        int total = 0;
        total = myRank.getTotal() - point.getPoint();
        System.out.println(total);

        myRank.setTotal(total);

        pointService.cancelPoint(accountId, todoId);
    }

//    //해당 점수 찾아서 유저 랭크 점수 다시 셋팅
//    @PutMapping("/cancelRankPoint/{accountId}/{todoId}")
//    public void cancelRankPoint(@PathVariable String accountId, @PathVariable String todoId) {
//        Point point = pointService.getPoint(accountId, todoId);
//        Rank myRank = rankService.getMyRank(accountId);
//        System.out.println(point.getPoint());
//
//        int total = 0;
//        total = myRank.getTotal() - point.getPoint();
//        System.out.println(total);
//
//        myRank.setTotal(total);
//
//
//    }

    //전체 유저 랭킹 리스트 조회 (여기서 리턴되는 랭킹 리스트로 프론트에서 순위 표시하기(일단은))

    @ApiOperation(value = "전체 유저 랭킹 리스트 조회", notes = "전체 유저의 전체 랭킹 리스트를 조회한다. 리턴된 유저 랭킹 리스트에서 인덱스로 순위 표시 프론트에서 함.")
    @GetMapping("/all/ranking")
    public List<RankDto> getAllAccountRanking() {
        return rankService.getUserAllRanking().stream().map(rank -> new RankDto(rank)).collect(Collectors.toList());
    }


    @ApiOperation(value = "선택한 유저의 랭킹 조회")
    @GetMapping("/myRanking/{accountId}")
    public RankDto getMyRanking(@PathVariable String accountId) {
        return new RankDto(rankService.getMyRank(accountId));
    }


    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody
    ErrorMessage runTimeError(RuntimeException e) {
        ErrorMessage error = new ErrorMessage();
        error.setMessage(e.getMessage());
        return error;
    }
}
