package com.mountain.voucherApp.engine.exit;

import com.mountain.voucherApp.io.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultExit implements ExitStrategy {

    private final Console console;

    @Autowired
    public DefaultExit(Console console) {
        this.console = console;
    }

    @Override
    public void exit() {
        console.close();
    }
}
