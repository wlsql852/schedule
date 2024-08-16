package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.ScheduleRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ScheduleService {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        Schedule saveSchedule = scheduleRepository.save(schedule);

        ScheduleResponseDto responseDto = new ScheduleResponseDto(saveSchedule);
        return responseDto;
    }

    public ScheduleResponseDto getSchedule(Long scheduleId) throws IllegalAccessException {
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        Schedule schedule = scheduleRepository.findById(scheduleId);
        if(schedule != null) {
            return new ScheduleResponseDto(schedule);
        }else {
            throw new IllegalAccessException("해당 일정은 존재하지 않습니다.");
        }
    }

    public List<ScheduleResponseDto> getSchedules(String updateDay, String managerName) {
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        return scheduleRepository.schedules(updateDay,managerName);

    }

    public ScheduleResponseDto update(Long id, ScheduleRequestDto requestDto) throws IllegalAccessException {
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        Schedule schedule = scheduleRepository.findById(id);
        if(schedule != null) {
            if(schedule.getPassword().equals(requestDto.getPassword())) {
                Schedule updateSchedule = scheduleRepository.update(id, requestDto);
                return new ScheduleResponseDto(updateSchedule);
            }else {
                throw new IllegalAccessException("비밀번호가 일치하지 않습니다.");
            }
        }else {
            throw new IllegalAccessException("해당 일정은 존재하지 않습니다.");
        }
    }

    public void delete(Long id, ScheduleRequestDto requestDto) throws IllegalAccessException {
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        Schedule schedule = scheduleRepository.findById(id);
        if(schedule != null) {
            if(schedule.getPassword().equals(requestDto.getPassword())) {
                scheduleRepository.delete(id);
            }else {
                throw new IllegalAccessException("비밀번호가 일치하지 않습니다.");
            }
        }else {
            throw new IllegalAccessException("해당 일정은 존재하지 않습니다.");
        }
    }


}
