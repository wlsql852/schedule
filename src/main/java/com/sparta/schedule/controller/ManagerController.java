package com.sparta.schedule.controller;

import com.sparta.schedule.dto.ManagerRequestDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ManagerController {

    private final JdbcTemplate jdbcTemplate;
    public ManagerController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
    * 6-1 매니저 등록
    * @Param 매니저 등록 정보
    * @Return 매니저 등록 정보
    * */
    //@PostMapping("/managers")
    //public ManagerResponseDto createManager(@RequestBody ManagerRequestDto requestDto) {

    //}

}
