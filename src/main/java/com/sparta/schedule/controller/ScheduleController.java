package com.sparta.schedule.controller;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.error.ValidateExceptionMsg;
import com.sparta.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    //1단계 : 일정 생성
    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule (@Valid @RequestBody ScheduleRequestDto requestDto, Errors errors) {
        if(errors.hasErrors()) {
            Map<String, String> validatorResult = new ValidateExceptionMsg().validateHandling(errors);
            Iterator<String> iter = validatorResult.keySet().iterator();
            String errorName = iter.next().toString();
            String errormsg = errorName.split("_")[1]+"(이)가 "+validatorResult.get(errorName);
            throw new ValidationException(errormsg);
        }else {
            return scheduleService.createSchedule(requestDto);
        }
    }

    //2단계 : 해당 일정 조회
    @GetMapping("/schedules/{scheduleId}")
    public ScheduleResponseDto getSchedule(@PathVariable Long scheduleId) throws IllegalAccessException {
        return scheduleService.getSchedule(scheduleId);

    }

    //3단계 : 일정 목록 조회
    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedules (@RequestParam(required = false)String updateDay, @RequestParam(required = false)String managerName) {
        return scheduleService.getSchedules(updateDay, managerName);
    }



    //4단계 : 선택한 일정 수정
    @PutMapping("/schedules/edit/{id}")
    public ScheduleResponseDto update(@PathVariable Long id, @Valid @RequestBody ScheduleRequestDto requestDto , Errors errors) throws IllegalAccessException {
        if(errors.hasErrors()) {
            Map<String, String> validatorResult = new ValidateExceptionMsg().validateHandling(errors);
            Iterator<String> iter = validatorResult.keySet().iterator();
            String errorName = iter.next().toString();
            String errormsg = errorName.split("_")[1]+"(이)가 "+validatorResult.get(errorName);
            throw new ValidationException(errormsg);
        }else {
            return scheduleService.update(id, requestDto);
        }
    }

    //5단계 : 선택한 일정 삭제
    @DeleteMapping("/schedules/delete/{id}")
    public void delete(@PathVariable Long id, @Valid @RequestBody ScheduleRequestDto requestDto, Errors errors) throws IllegalAccessException {
        if(errors.hasErrors()) {
            Map<String, String> validatorResult = new ValidateExceptionMsg().validateHandling(errors);
            Iterator<String> iter = validatorResult.keySet().iterator();
            String errorName = iter.next().toString();
            String errormsg = errorName.split("_")[1]+"(이)가 "+validatorResult.get(errorName);
            throw new ValidationException(errormsg);
        }else {
            scheduleService.delete(id, requestDto);
        }
    }
    /*
     * 7단계 : 페이지 번호와 크기를 쿼리로 받아 해당 페이지의 스케줄 메모 출력
     * @param : 페이지 크기, 번호
     * @return : 해당 페이지의 목록*/
    @GetMapping("/schedules/{pagesize}/{page}")
    public List<ScheduleResponseDto> getManagers(@PathVariable int pagesize, @PathVariable int page) {
        return scheduleService.getschedulespaging(pagesize, page);
    }
}
