package com.programmers.application.controller.console;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ExitController implements Controller{
    private static final int ABNORMAL_EXIT = 1;

    @Override
    public void process() throws IOException {
        System.exit(ABNORMAL_EXIT);
    }
}
