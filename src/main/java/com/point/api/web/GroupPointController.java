package com.point.api.web;

import com.point.api.domain.GroupPoint;
import com.point.api.domain.GroupRank;
import com.point.api.domain.InGroupRank;
import com.point.api.domain.service.GroupPointService;
import com.point.api.domain.service.GroupRankService;
import com.point.api.domain.service.InGroupRankService;
import com.point.api.web.dto.GroupPointAddDto;
import com.point.api.web.dto.GroupPointDto;
import com.point.api.web.dto.GroupRankDto;
import com.point.api.web.dto.InGroupRankDto;
import com.point.api.web.message.ErrorMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"2. GroupPoint"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/points/groups/") //컨트롤러 기본 URL
public class GroupPointController {
    private final GroupPointService groupPointService;
    private final GroupRankService groupRankService;
    private final InGroupRankService inGroupRankService;

    // GroupPointList 정상적으로 조회 되는지 테스트
    @ApiOperation(value = "그룹 포인트 테스트", notes = "GroupPointList 정상적으로 조회 되는지 테스트")
    @GetMapping("")
    public List<GroupPointDto> getPointTest(){
        return groupPointService.getAllGroupPoint().stream().map(GroupPoint -> new GroupPointDto(GroupPoint)).collect(Collectors.toList());
    }

    //그룹 점수 산정 후 추가
    @ApiOperation(value = "그룹 점수 이력, 그룹 점수 합산, 내그룹 점수 산정 후 추", notes = "점수 부여 기준에 따라 산정된 점수가 그룹 점수 테이블에 추가, 기존 점수 + 산정된 점수 그룹랭킹 테이블에 추가, 기존 점수+산정된 점수 인그룹 랭킹 테이블에 추가")
    @PostMapping("")
    public GroupPointDto addPoint(@RequestBody GroupPointAddDto groupPointAddDto){
        GroupPoint groupPoint = groupPointAddDto.toDomain();
        String groupId = groupPointAddDto.getGroupId();
        String accountId = groupPointAddDto.getAccountId();
        int likeCount = groupPointAddDto.getLikeCount();

        int pointValue = 0;

        //오늘 하루동안 완료한  개수 가져오기
        int todayCompletedCount = groupPointService.getTodayCompletedCount(accountId, groupId);

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
        groupPoint.setPoint(pointValue);
        // 점수 생성
        GroupPointDto groupPointDto = new GroupPointDto(groupPointService.addGroupPoint(groupPoint));
        groupRankService.sumGroupPoint(groupId, pointValue);
        inGroupRankService.sumInGroupPoint(accountId, groupId, pointValue);


        return groupPointDto;
    }


    //그룹 점수 삭제
    @ApiOperation(value = "그룹 점수 삭제")
    @GetMapping("cancel/{accountId}/{groupId}/{todoId}")
    public void cancelGroupPoint (@PathVariable String accountId, @PathVariable String groupId, @PathVariable String todoId) {

        groupPointService.deleteGroupPoint(accountId, groupId, todoId);
    }


    //특정 그룹 점수 리스트 조회
    @ApiOperation(value = "특정 그룹 점수 리스트 조회")
    @GetMapping("{groupI제d}")
    public List<GroupPointDto> getGroupPointList (@PathVariable String groupId) {
        List<GroupPoint> groupPointList = groupPointService.getGroupAllPoint(groupId);
        return groupPointList.stream().map(groupPoint -> new GroupPointDto(groupPoint)).collect(Collectors.toList());
    }

    //모든 그룹 랭킹 리스트 조회
    @ApiOperation(value = "모든 그룹 점수 랭킹 리스트 조회")
    @GetMapping("/all/groupRanking")
    public List<GroupRankDto> getGroupAllRanking () {
        return groupRankService.getGroupAllRanking().stream().map(groupRank -> new GroupRankDto(groupRank)).collect(Collectors.toList());
    }

    //특정 그룹 내 유저 랭킹 리스트 조회 --> 리턴된 리스트에서 인덱스로 순위 표시 프론트에서 함.
    @ApiOperation(value = "특정 그룹 내 유저 랭킹 리스트 조회", notes = "리턴된 리스트에서 인덱스로 순위 표시 프론트에서 함.")
    @GetMapping("all/inGroupRanking/{groupId}")
    public List<InGroupRankDto> getInGroupAllRanking(@PathVariable String groupId) {
        return inGroupRankService.getInGroupAllRanking(groupId).stream().map(inGroupRank -> new InGroupRankDto(inGroupRank)).collect(Collectors.toList());
    }

    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody
    ErrorMessage runTimeError(RuntimeException e) {
        ErrorMessage error = new ErrorMessage();
        error.setMessage(e.getMessage());
        return error;
    }


}
