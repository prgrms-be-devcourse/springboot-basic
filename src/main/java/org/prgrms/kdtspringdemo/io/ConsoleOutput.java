package org.prgrms.kdtspringdemo.io;

import org.springframework.stereotype.Component;

@Component
public class ConsoleOutput implements Output {
    @Override
    public void printPrompt(String prompt) {
        System.out.print(prompt);
    }

    @Override
    public void printText(String s) {
        System.out.println(s);
    }

    @Override
    public void printError(Exception e) {
        System.out.println(e.getMessage());
    }
}
