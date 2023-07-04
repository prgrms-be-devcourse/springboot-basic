package com.example.voucher.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Input {
    public String readInput() {
        String input = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            input = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return input;
    }
}

