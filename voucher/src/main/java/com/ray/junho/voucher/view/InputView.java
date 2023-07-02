package com.ray.junho.voucher.view;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class InputView {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String read() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
