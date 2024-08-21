package com.sparta.schedule.controller;

import com.sparta.schedule.dto.ManagerRequestDto;
import com.sparta.schedule.dto.ManagerResponseDto;
import com.sparta.schedule.error.ValidateExceptionMsg;
import com.sparta.schedule.service.ManagerService;
import com.sparta.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ManagerController {

    private final ManagerService managerService;
    private final ScheduleService scheduleService;

    public ManagerController(ManagerService managerService, ScheduleService scheduleService) {
        this.managerService = managerService;
        this.scheduleService = scheduleService;
    }

    /*
    * 6-1 매니저 등록
    * @Param 매니저 등록 정보
    * @Return 매니저 등록 정보
    * */
    @PostMapping("/managers")
    public ManagerResponseDto createManager(@Valid @RequestBody ManagerRequestDto requestDto, Errors errors) {
        if(errors.hasErrors()) {
            String errormsg = new ValidateExceptionMsg().getVaildExceptionMsg(errors);
            throw new ValidationException(errormsg);
        }else {
            return managerService.createManager(requestDto);
        }
    }

    /*
    * 6단계 : 해당 매니저 조회
    * @Param 검색할 매니저 아이디
    * @Return 해당 매니저 정보*/
    @GetMapping("/managers/{managerId}")
    public ManagerResponseDto getManager(@PathVariable Long managerId) throws IllegalAccessException {
        return managerService.getManager(managerId);
    }

    /*
     * 추가 : 매니저 목록 조회
     * @Param 수정일, 매니저 이름
     * @Return 일정 정보 목록
     * */
    @GetMapping("/managers")
    public List<ManagerResponseDto> getSchedules () {
        return managerService.getSchedules();
    }

    /*
     * 추가 : 선택한 매니저 수정
     * @Param 수정한 매니저 등록 정보
     * @Return 일정 등록 정보
     * */
    @PutMapping("/managers/edit/{id}")
    public ManagerResponseDto update(@PathVariable Long id, @Valid @RequestBody ManagerRequestDto requestDto, Errors errors) throws IllegalAccessException {
        //@Valid를 충족시키지 않으면
        if(errors.hasErrors()) {
            //해당 문제의 메세지를 만들어 오류 날리기
            String errormsg = new ValidateExceptionMsg().getVaildExceptionMsg(errors);
            throw new ValidationException(errormsg);
        }else {
            return managerService.update(id, requestDto);
        }
    }

    /*
     * 추가 : 선택한 매니저 삭제
     * @Param 매니저 아이디
     * @Return 없음
     * */
    @DeleteMapping("/managers/delete/{id}")
    public void delete(@PathVariable Long id) throws IllegalAccessException {
        managerService.delete(id);
    }



}
