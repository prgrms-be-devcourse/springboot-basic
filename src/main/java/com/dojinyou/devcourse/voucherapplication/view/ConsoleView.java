package com.dojinyou.devcourse.voucherapplication.view;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleView implements InputView<String>, OutputView<String> {
    private static final String EMPTY_INPUT_ERROR_MESSAGE = "ERROR: 입력이 없습니다";
    private static final String EMPTY_OUTPUT_ERROR_MESSAGE = "ERROR: 출력이 없습니다";
    private Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String getUserInput() {
        String userInput = scanner.nextLine().strip();
        validateEmptyInput(userInput);
        return userInput;
    }
    
    private void validateEmptyInput(String userInput) {
        if(userInput.length() == 0) {
            throw new IllegalArgumentException(EMPTY_INPUT_ERROR_MESSAGE);
        }
    }

    @Override
    public void disPlay(String output) {
        output = output.trim();
        validateEmptyOutput(output);
        System.out.print(output+"\n");
    }

    private void validateEmptyOutput(String output) {
        if(output.length() == 0) {
            throw new IllegalArgumentException(EMPTY_OUTPUT_ERROR_MESSAGE);
        }
    }
}
