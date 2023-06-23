package org.weekly.weekly.ui.writer;

import org.springframework.stereotype.Component;

@Component
public class CommandWriter {
    private void println(String msg) {
        System.out.println(msg);
    }
    private void print(String msg) {
        System.out.print(msg);
    }
}
