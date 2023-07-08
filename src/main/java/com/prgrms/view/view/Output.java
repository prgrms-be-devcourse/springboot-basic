package com.prgrms.view.view;

import org.springframework.stereotype.Component;

@Component
public class Output {
    public void write(String message) {
        System.out.println(message);
    }
}
