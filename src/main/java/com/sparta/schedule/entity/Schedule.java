package com.sparta.schedule.entity;

import com.sparta.schedule.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private Long id;
    private String todo;
    private String manager;
    private String password;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;



    public Schedule(ScheduleRequestDto requestDto) {
        this.todo = requestDto.getTodo();
        this.manager = requestDto.getManager();
        this.password = requestDto.getPassword();
        LocalDateTime now = LocalDateTime.now();
        this.createDate = now;
        this.updateDate = now;
    }
}
