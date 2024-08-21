package com.sparta.schedule.controller;

import com.sparta.schedule.dto.ManagerRequestDto;
import com.sparta.schedule.dto.ManagerResponseDto;
import com.sparta.schedule.error.ValidateExceptionMsg;
import com.sparta.schedule.service.ManagerService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
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




}
