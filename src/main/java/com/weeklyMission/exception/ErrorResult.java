package com.weeklyMission.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResult {

    int code;
    String message;

}
