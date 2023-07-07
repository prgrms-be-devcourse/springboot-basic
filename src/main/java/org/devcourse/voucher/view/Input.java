package org.devcourse.voucher.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Input {
  
    public String getUserInput() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("입력 도중 에러 발생");
        }
    }
}
