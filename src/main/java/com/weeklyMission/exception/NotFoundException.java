package com.weeklyMission.exception;

import java.util.NoSuchElementException;

public class NotFoundException extends NoSuchElementException {

    public NotFoundException(String Message) {
        super(Message);
    }
}
