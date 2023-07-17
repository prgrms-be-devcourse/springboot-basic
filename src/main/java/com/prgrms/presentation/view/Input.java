package com.prgrms.presentation.view;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

    public int enterID() {
        try {
            String input = bufferedReader.readLine();
            return Integer.parseInt(input);
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

