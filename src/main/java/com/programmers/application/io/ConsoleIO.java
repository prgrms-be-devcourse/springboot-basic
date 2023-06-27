package com.programmers.application.io;

import org.springframework.stereotype.Component;

@Component
public class ConsoleIO implements IO{
    @Override
    public void write(String input) {

    }

    @Override
    public String read() {
        return null;
    }
}
