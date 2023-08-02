package com.prgrms.presentation.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.springframework.stereotype.Component;

@Component
public class Input {

    private final BufferedReader bufferedReader;

    public Input() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public String enterOption() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String enterID() {
        try {
            String input = bufferedReader.readLine();
            return input;
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public Double enterDiscount() {
        try {
            String input = bufferedReader.readLine();
            return Double.parseDouble(input);
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

}

