package com.prgrms.w3springboot.io;

import com.prgrms.w3springboot.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

@Component
public class CommandLine implements Runnable {
    private final Console console;
    private final VoucherService voucherService;

    public CommandLine(final Console console, final VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    @Override
    public void run() {
        boolean flag = true;
        while (flag) {
            console.printInit();
            String commandType = console.input();
            try {
                flag = CommandType.of(commandType).execute(console, voucherService);
            } catch (Exception e) {
                console.printInvalidMessage();
            }
        }
    }
}
