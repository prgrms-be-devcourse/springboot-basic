package org.prgrms.kdtspringdemo.view.console.output;

import org.springframework.stereotype.Component;

@Component
public class PrintOutput implements Output {
    @Override
    public <T> void write(T value) {
        System.out.print(value);
    }
}
