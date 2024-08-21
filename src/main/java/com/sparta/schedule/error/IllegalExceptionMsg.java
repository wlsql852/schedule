package com.sparta.schedule.error;

import lombok.Getter;

@Getter
public class IllegalExceptionMsg {
    private String msg;
    public IllegalExceptionMsg(String msg) {
        this.msg = msg;
    }
}
