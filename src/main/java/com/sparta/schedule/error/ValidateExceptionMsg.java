package com.sparta.schedule.error;

import lombok.Getter;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Iterator;
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

    public String getVaildExceptionMsg(Errors errors) {
        Map<String, String> validatorResult = validateHandling(errors);
        Iterator iter = validatorResult.keySet().iterator();
        String errorName = iter.next().toString();
        return errorName.split("_")[1]+"(이)가 "+validatorResult.get(errorName);
    }
}
