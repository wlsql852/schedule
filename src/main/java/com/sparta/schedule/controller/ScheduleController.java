package com.sparta.schedule.controller;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.service.ScheduleService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleController(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //1단계 : 일정 생성
    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule (@RequestBody ScheduleRequestDto requestDto) {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.createSchedule(requestDto);
    }

    //2단계 : 해당 일정 조회
    @GetMapping("/schedules/{scheduleId}")
    public ScheduleResponseDto getSchedule(@PathVariable Long scheduleId) throws IllegalAccessException {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.getSchedule(scheduleId);

    }

    //3단계 : 일정 목록 조회
    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedules (@RequestParam(required = false)String updateDay, @RequestParam(required = false)String managerName) {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.getSchedules(updateDay, managerName);
    }



    //4단계 : 선택한 일정 수정
    @PutMapping("/schedules/edit/{id}")
    public ScheduleResponseDto update(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) throws IllegalAccessException {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.update(id, requestDto);
    }

    //5단계 : 선택한 일정 삭제
    @DeleteMapping("/schedules/delete/{id}")
    public void delete(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) throws IllegalAccessException {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        scheduleService.delete(id, requestDto);
    }




}
