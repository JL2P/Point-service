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
    @GetMapping()
    public List<GroupPointDto> getPointTest(){
        return groupPointService.getAllGroupPoint().stream().map(GroupPoint -> new GroupPointDto(GroupPoint)).collect(Collectors.toList());
    }

    @PostMapping
    public GroupPointDto addPoint(@RequestBody GroupPointAddDto groupPointAddDto){
        GroupPoint newGroupPoint = groupPointAddDto.toDomain();
        newGroupPoint.setPoint(100); // 이부분에 계산한 값이 들어가야함

        return new GroupPointDto(groupPointService.addGroupPoint(newGroupPoint));
    }

    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody
    ErrorMessage runTimeError(RuntimeException e) {
        ErrorMessage error = new ErrorMessage();
        error.setMessage(e.getMessage());
        return error;
    }
}
