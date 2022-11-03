package org.prgrms.kdt.io;

import org.springframework.stereotype.Component;

@Component
public class Output {
    public void outputText(String text) {
        System.out.println(text);
    }
}
