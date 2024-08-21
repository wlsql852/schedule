package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Manager;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ManagerResponseDto {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public ManagerResponseDto(Manager manager) {
        this.id = manager.getId();
        this.name = manager.getName();
        this.email = manager.getEmail();
        this.createDate = manager.getCreateDate();
        this.updateDate = manager.getUpdateDate();
    }

    public ManagerResponseDto(Long id, String name, String email, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
