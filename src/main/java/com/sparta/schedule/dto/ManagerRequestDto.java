package com.sparta.schedule.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class ManagerRequestDto {
    private String name;
    @Email
    private String email;
}
