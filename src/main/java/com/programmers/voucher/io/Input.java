package com.programmers.voucher.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Input {
    private final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public String input() throws IOException {
        return br.readLine();
    }
}
