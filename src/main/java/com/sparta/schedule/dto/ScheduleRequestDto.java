package com.sparta.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class ScheduleRequestDto {
    private String todo;
    private String password;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
