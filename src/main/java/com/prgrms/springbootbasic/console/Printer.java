package com.prgrms.springbootbasic.console;

import org.springframework.stereotype.Component;

@Component
public class Printer {

    public Printer() {
    }

    public void printMessage(String message){
        System.out.println(message);
    }
}
