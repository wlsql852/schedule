package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ManagerService managerService;

    public ScheduleService(ScheduleRepository scheduleRepository, ManagerService managerService) {
        this.scheduleRepository = scheduleRepository;
        this.managerService = managerService;
    }



    //스케줄 생성
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);
        //레포지토리에서 스케줄 생성하기
        Schedule saveSchedule = scheduleRepository.save(schedule);
        //생성된 스케줄을 responseDto형태로 바꿔서 내보내기
        ScheduleResponseDto responseDto = new ScheduleResponseDto(saveSchedule, managerService.getManagerNameById(saveSchedule.getManagerId()));
        return responseDto;
    }
    //단건 조회
    public ScheduleResponseDto getSchedule(Long scheduleId) throws IllegalAccessException {
        //해당 일정 찾아 가져오기
        Schedule schedule = scheduleRepository.findById(scheduleId);
        //있으면 해당 일정 responseDto형태로 내보내기
        if(schedule != null) {
            return new ScheduleResponseDto(schedule, managerService.getManagerNameById(schedule.getManagerId()));
        }else {
            //없으면 에러
            throw new IllegalAccessException("해당 일정은 존재하지 않습니다.");
        }
    }
    //다건 조회
    public List<ScheduleResponseDto> getSchedules(String updateDay, String managerName){
        //매니저 이름으로 id값 찾기
        Long managerId = managerService.getManagerIdByName(managerName);
        //데이터에서 updateDay와 managerName으로 검색한 데이터를 리스트 형태로 가져오기
        return scheduleRepository.schedules(updateDay,managerId);

    }
    //일정 수정
    public ScheduleResponseDto update(Long id, ScheduleRequestDto requestDto) throws IllegalAccessException {
        //해당 일정 찾기
        Schedule schedule = scheduleRepository.findById(id);
        if(schedule != null) {
            //해당 일정이 있으면 비밀번호 일치 확인
            if(schedule.getPassword().equals(requestDto.getPassword())) {
                //비밀번호까지 일치하면 수정 후 수정된 정보 가져오기
                Schedule updateSchedule = scheduleRepository.update(id, requestDto);
                //수정된 정보를 responseDto로 보내주기
                return new ScheduleResponseDto(updateSchedule, managerService.getManagerNameById(updateSchedule.getManagerId()));
            }else {
                throw new IllegalAccessException("비밀번호가 일치하지 않습니다.");
            }
        }else {
            throw new IllegalAccessException("해당 일정은 존재하지 않습니다.");
        }
    }

    //일정 삭제
    public void delete(Long id, ScheduleRequestDto requestDto) throws IllegalAccessException {
        //해당 일정 찾기
        Schedule schedule = scheduleRepository.findById(id);
        if(schedule != null) {
            //일정이 있으면 비밀번호 일치 확인
            if(schedule.getPassword().equals(requestDto.getPassword())) {
                //비밀번호 일치시 데이터 삭제
                scheduleRepository.delete(id);
            }else {
                throw new IllegalAccessException("비밀번호가 일치하지 않습니다.");
            }
        }else {
            throw new IllegalAccessException("해당 일정은 존재하지 않습니다.");
        }
    }


    public List<ScheduleResponseDto> getschedulespaging(int pagesize, int page) {
        return scheduleRepository.getschedulespaging(pagesize, page);
    }
}
