package org.prgrms.kdt.app.configuration.io;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class ConsoleInputHandler implements InputHandler{

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String inputString() throws IOException {
        return br.readLine();
    }

    @Override
    public int inputInt() throws IOException {
        return Integer.parseInt(br.readLine());
    }

}
