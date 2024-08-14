package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class ScheduleResponseDto {
    private Long id;
    private String todo;
    private String manager;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.todo = schedule.getTodo();
        this.manager = schedule.getManager();
        this.createDate = schedule.getCreateDate();
        this.updateDate = schedule.getUpdateDate();
    }

    public ScheduleResponseDto(Long id, String todo,String manager, Object createDate, Object updateDate) {
        this.id = id;
        this.todo = todo;
        this.manager = manager;
        this.createDate = (LocalDateTime) createDate;
        this.updateDate = (LocalDateTime) updateDate;
    }
}
