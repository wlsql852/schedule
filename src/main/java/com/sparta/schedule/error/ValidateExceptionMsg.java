package com.sparta.schedule.error;

import lombok.Getter;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ValidateExceptionMsg {
    private String msg;
    public ValidateExceptionMsg() {}
    public ValidateExceptionMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());        }
        return validatorResult;
    }
}
