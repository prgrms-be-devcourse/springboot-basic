package com.prgrms.springbootbasic.console;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class Printer {

    public Printer() {
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
