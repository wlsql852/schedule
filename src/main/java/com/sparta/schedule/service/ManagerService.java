package com.sparta.schedule.service;

import com.sparta.schedule.dto.ManagerRequestDto;
import com.sparta.schedule.dto.ManagerResponseDto;
import com.sparta.schedule.entity.Manager;
import com.sparta.schedule.repository.ManagerRepository;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
    private final ManagerRepository managerRepository;

    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public ManagerResponseDto createManager(ManagerRequestDto requestDto) {
        Manager manager = new Manager(requestDto);
        //레포지토리에서 매니저 데이터 생성
        Manager saveManager = managerRepository.save(manager);
        //생성된 매니저데이터를 responseDto형태로 바꿔서 내보내기
        ManagerResponseDto responseDto = new ManagerResponseDto(saveManager);
        return responseDto;
    }

    public ManagerResponseDto getManager(Long managerId) throws IllegalAccessException {
        Manager manager = managerRepository.findById(managerId);
        //있으면 responseDto형태로 내보내기
        if (manager != null) {
            return new ManagerResponseDto(manager);
        }else {
            throw new IllegalAccessException("해당 매니저는 없습니다");
        }
    }
    public Long getManagerIdByName(String name) {
        return managerRepository.getManagerIdByName(name);
    }
    public String getManagerNameById(Long managerId) {
        return managerRepository.getManagerNameById(managerId);
    }

}
