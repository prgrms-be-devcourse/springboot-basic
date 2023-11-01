package com.weeklyMission.exception;

public class AlreadyExistsException extends RuntimeException{

    private static final String MESSAGE = "이미 존재하는 요소입니다.";

    public AlreadyExistsException() {
        super(MESSAGE);
    }
}
