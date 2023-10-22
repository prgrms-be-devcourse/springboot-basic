package com.weeklyMission.exception;

import java.util.NoSuchElementException;

public class IncorrectInputException extends NoSuchElementException {
    private final String quest;
    private final String input;

    public IncorrectInputException(String quest, String input, String message) {
        super(message);
        this.quest = quest;
        this.input = input;
    }

    public String getQuest() {
        return quest;
    }

    public String getInput() {
        return input;
    }
}
