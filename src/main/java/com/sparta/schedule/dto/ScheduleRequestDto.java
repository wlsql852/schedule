package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;


@Getter
public class ScheduleRequestDto {
    @NotBlank
    @Size(min = 1, max = 200)
    private String todo;
    private Long managerId;
    @NotBlank
    private String password;
}
