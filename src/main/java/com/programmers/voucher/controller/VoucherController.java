package com.programmers.voucher.controller;

import com.programmers.voucher.uitl.MenuType;
import com.programmers.voucher.view.Console;
import org.springframework.stereotype.Component;

@Component
public class VoucherController {
    private boolean isRunning = true;
    private Console console = new Console();

    public void run() {
        while (isRunning) {
            MenuType.getCommand(console.getRequest());
        }
    }
}
