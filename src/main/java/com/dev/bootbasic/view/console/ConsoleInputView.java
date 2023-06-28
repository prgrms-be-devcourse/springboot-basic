package com.dev.bootbasic.view.console;

import com.dev.bootbasic.view.InputView;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class ConsoleInputView implements InputView {

    private static final String RETRY_INPUT_MESSAGE = "오류가 발생했습니다. 다시 입력해주세요.";
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String inputLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new IllegalArgumentException(RETRY_INPUT_MESSAGE);
        }
    }

}
