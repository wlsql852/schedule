package com.sparta.schedule.entity;

import com.sparta.schedule.dto.ManagerRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Manager {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public Manager(ManagerRequestDto requestDto) {
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
        LocalDateTime now = LocalDateTime.now();
        this.createDate = now;
        this.updateDate = now;
    }
}
