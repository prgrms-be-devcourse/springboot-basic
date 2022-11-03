package org.prgrms.kdt.io;

import org.springframework.stereotype.Component;

@Component
public class Output {

    public void printText(String text) {
        System.out.println(text);
    }
}
