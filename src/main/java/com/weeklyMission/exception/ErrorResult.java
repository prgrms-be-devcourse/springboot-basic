package com.weeklyMission.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResult {

    Integer code;
    String message;

}
