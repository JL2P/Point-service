package com.point.api.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class TimeTermDto {
    private String accountId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
