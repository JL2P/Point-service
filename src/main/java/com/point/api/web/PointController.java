package com.point.api.web;

import com.point.api.domain.Point;
import com.point.api.domain.service.PointService;
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

    @PostMapping
    public PointDto addPoint(@RequestBody PointAddDto pointAddDto){
        Point newPoint = pointAddDto.toDomain();
        newPoint.setPoint(100); // 이부분에 계산한 값이 들어가야함

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
