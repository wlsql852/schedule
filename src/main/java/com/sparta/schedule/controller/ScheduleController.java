package com.sparta.schedule.controller;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.error.ValidateExceptionMsg;
import com.sparta.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
    
    /*
     * 1단계 : 일정 생성
     * @Param 일정 등록 정보
     * @Return 일정 등록 정보
     * */
    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule (@Valid @RequestBody ScheduleRequestDto requestDto, Errors errors) {
        //@Valid를 충족시키지 않으면
        if(errors.hasErrors()) {
            //해당 문제의 메세지를 만들어 오류 날리기
            String errormsg = new ValidateExceptionMsg().getVaildExceptionMsg(errors);
            throw new ValidationException(errormsg);
        }else {
            return scheduleService.createSchedule(requestDto);
        }
    }

    /*
     * 2단계 : 해당 일정 조회
     * @Param 검색할 일정 아이디
     * @Return 일정 등록 정보
     * */
    @GetMapping("/schedules/{scheduleId}")
    public ScheduleResponseDto getSchedule(@PathVariable Long scheduleId) throws IllegalAccessException {
        return scheduleService.getSchedule(scheduleId);

    }

    /*
     * 3단계 : 일정 목록 조회
     * @Param 수정일, 매니저 이름
     * @Return 일정 정보 목록
     * */
    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedules (@RequestParam(required = false)String updateDay, @RequestParam(required = false)String managerName) {
        return scheduleService.getSchedules(updateDay, managerName);
    }



    /*
     * 4단계 : 선택한 일정 수정
     * @Param 수정한 일정 등록 정보
     * @Return 일정 등록 정보
     * */
    @PutMapping("/schedules/edit/{id}")
    public ScheduleResponseDto update(@PathVariable Long id, @Valid @RequestBody ScheduleRequestDto requestDto , Errors errors) throws IllegalAccessException {
        //@Valid를 충족시키지 않으면
        if(errors.hasErrors()) {
            //해당 문제의 메세지를 만들어 오류 날리기
            String errormsg = new ValidateExceptionMsg().getVaildExceptionMsg(errors);
            throw new ValidationException(errormsg);
        }else {
            return scheduleService.update(id, requestDto);
        }
    }

    /*
     * 5단계 : 선택한 일정 삭제
     * @Param 일정 아이디와 비밀번호가 담긴 request
     * @Return 없음
     * */
    @DeleteMapping("/schedules/delete/{id}")
    public void delete(@PathVariable Long id, @Valid @RequestBody ScheduleRequestDto requestDto, Errors errors) throws IllegalAccessException {
        //@Valid를 충족시키지 않으면
        if(errors.hasErrors()) {
            //해당 문제의 메세지를 만들어 오류 날리기
            String errormsg = new ValidateExceptionMsg().getVaildExceptionMsg(errors);
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
