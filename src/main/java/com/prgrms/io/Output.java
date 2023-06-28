package com.prgrms.io;

import org.springframework.stereotype.Component;

@Component
public class Output {
    public void outputView(String message) {
        System.out.println(message);
    }
}
