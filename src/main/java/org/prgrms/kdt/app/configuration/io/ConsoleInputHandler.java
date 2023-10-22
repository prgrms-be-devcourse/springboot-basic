package org.prgrms.kdt.app.configuration.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ConsoleInputHandler implements InputHandler{

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String inputString() throws IOException {
        return br.readLine();
    }
}
