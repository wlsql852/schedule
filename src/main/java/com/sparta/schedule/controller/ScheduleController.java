package com.sparta.schedule.controller;

import com.sparta.schedule.dto.*;
import com.sparta.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
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
        Schedule schedule = new Schedule(requestDto);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO schedule (todo, manager, password, createDate, updateDate) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, schedule.getTodo());
            ps.setString(2, schedule.getManager());
            ps.setString(3, schedule.getPassword());
            ps.setObject(4, schedule.getCreateDate());
            ps.setObject(5, schedule.getUpdateDate());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        schedule.setId(id);

        ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);
        return responseDto;
    }

    //2단계 : 해당 일정 조회
    @GetMapping("/schedules/{scheduleId}")
    public ScheduleResponseDto getSchedule(@PathVariable Long scheduleId) throws IllegalAccessException {
        Schedule schedule = findById(scheduleId);
        if(schedule != null) {
            return new ScheduleResponseDto(schedule);
        }else {
            throw new IllegalAccessException("해당 일정은 존재하지 않습니다.");
        }
    }

    //3단계 : 일정 목록 조회
    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedules (@RequestParam(required = false)String UpdateDay, @RequestParam(required = false)String managerName) {
        //둘다 있는 경우
        if(UpdateDay!=null && managerName != null) {
            String sql = "SELECT * FROM schedule WHERE UpdateDate is ? AND manager = ?";
            return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
                @Override
                public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Long id = rs.getLong("scheduleId");
                    String todo = rs.getString("todo");
                    String manager = rs.getString("manager");
                    Object createDate = rs.getObject("createDate");
                    Object updateDate = rs.getObject("updateDate");
                    return new ScheduleResponseDto(id, todo, manager, createDate, updateDate);
                }
            }, UpdateDay, managerName);
            //수정날짜만 있는 경우
        } else if (UpdateDay!=null) {
            String sql = "SELECT * FROM schedule WHERE UpdateDate is ?";
            return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
                @Override
                public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Long id = rs.getLong("scheduleId");
                    String todo = rs.getString("todo");
                    String manager = rs.getString("manager");
                    Object createDate = rs.getObject("createDate");
                    Object updateDate = rs.getObject("updateDate");
                    return new ScheduleResponseDto(id, todo, manager, createDate, updateDate);
                }
            }, UpdateDay);
            //매니저 이름만 있는 경우
        } else if (managerName!=null) {
            String sql = "SELECT * FROM schedule WHERE manager = ?";
            return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
                @Override
                public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Long id = rs.getLong("scheduleId");
                    String todo = rs.getString("todo");
                    String manager = rs.getString("manager");
                    Object createDate = rs.getObject("createDate");
                    Object updateDate = rs.getObject("updateDate");
                    return new ScheduleResponseDto(id, todo, manager, createDate, updateDate);
                }
            }, managerName);
        }else {
            String sql = "SELECT * FROM schedule ";
            return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
                @Override
                public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Long id = rs.getLong("scheduleId");
                    String todo = rs.getString("todo");
                    String manager = rs.getString("manager");
                    Object createDate = rs.getObject("createDate");
                    Object updateDate = rs.getObject("updateDate");
                    return new ScheduleResponseDto(id, todo, manager, createDate, updateDate);
                }
            });
        }
    }

    private Schedule findById (Long id) {
        String sql = "SELECT * FROM schedule WHERE scheduleId = ?";
        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getLong("scheduleId"));
                schedule.setTodo(resultSet.getString("todo"));
                schedule.setManager(resultSet.getString("manager"));
                schedule.setPassword(resultSet.getString("password"));
                schedule.setCreateDate((LocalDateTime) resultSet.getObject("createDate"));
                schedule.setUpdateDate((LocalDateTime) resultSet.getObject("updateDate"));
                return schedule;
            }else {
                return null;
            }
        },id);
    }


}
