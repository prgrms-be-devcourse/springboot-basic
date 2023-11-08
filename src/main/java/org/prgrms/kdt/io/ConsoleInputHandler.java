package org.prgrms.kdt.io;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.prgrms.kdt.io.SystemMessage.EXCEPTION_INPUT;

@Component
public class ConsoleInputHandler implements InputHandler{

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String inputString() {
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(EXCEPTION_INPUT.getMessage());
        }
    }

    @Override
    public int inputInt() {
        try {
            return Integer.parseInt(br.readLine());
        } catch (IOException e) {
            throw new RuntimeException(EXCEPTION_INPUT.getMessage());
        }
    }

}
