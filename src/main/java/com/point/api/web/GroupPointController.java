package com.point.api.web;

import com.point.api.domain.GroupPoint;
import com.point.api.domain.service.GroupPointService;
import com.point.api.web.dto.GroupPointAddDto;
import com.point.api.web.dto.GroupPointDto;
import com.point.api.web.message.ErrorMessage;
import io.swagger.annotations.Api;
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


    // GroupPointList 정상적으로 조회 되는지 테스트
    @GetMapping("")
    public List<GroupPointDto> getPointTest(){
        return groupPointService.getAllGroupPoint().stream().map(GroupPoint -> new GroupPointDto(GroupPoint)).collect(Collectors.toList());
    }

    //그룹포인트 산정 후 그룹점수 이력 추가
    @PostMapping("")
    public GroupPointDto addPoint(@RequestBody GroupPointAddDto groupPointAddDto){
        GroupPoint groupPoint = groupPointAddDto.toDomain();
        String groupId = groupPointAddDto.getGroupId();
        String accountId = groupPointAddDto.getAccountId();
        int likeCount = groupPointAddDto.getLikeCount();

        int pointValue = 0;

        //오늘 하루동안 완료한  개수 가져오기
        int todayCompletedCount = groupPointService.getTodayCompletedCount(groupId);

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

        return groupPointDto;
    }

    //그룹 점수 전체 이력 조회
    @GetMapping("/{groupId}")
    public List<GroupPointDto> getGroupPointList (@PathVariable String groupId) {
        List<GroupPoint> groupPointList = groupPointService.getGroupAllPoint(groupId);
        return groupPointList.stream().map(groupPoint -> new GroupPointDto(groupPoint)).collect(Collectors.toList());
    }


   //그룹별 랭킹 조회



    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody
    ErrorMessage runTimeError(RuntimeException e) {
        ErrorMessage error = new ErrorMessage();
        error.setMessage(e.getMessage());
        return error;
    }
}
