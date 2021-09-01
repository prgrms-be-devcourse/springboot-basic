package org.prgrms.kdtspringhomework.command;

import org.prgrms.kdtspringhomework.io.Console;
import org.prgrms.kdtspringhomework.io.Input;
import org.prgrms.kdtspringhomework.io.Output;
import org.prgrms.kdtspringhomework.voucher.service.VoucherService;
import org.springframework.context.ApplicationContext;

public class CommandLine implements Runnable {

    private final Input input;
    private final Output output;
    private final ApplicationContext applicationContext;
    private final String FIXED = "fixed";
    private final String PERCENT = "percent";

    public CommandLine(Console console, ApplicationContext applicationContext) {
        this.input = console;
        this.output = console;
        this.applicationContext = applicationContext;
    }

    @Override
    public void run() {
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);

        //프로그램 시작
        selectMenu(voucherService);
    }

    private void selectMenu(VoucherService voucherService) {
        while (true) {
            output.start();
            output.inputTypeMessage();
            try {
                String userCommand = input.input();
                CommandStatus.execute(input, output, voucherService, userCommand);
            } catch (IllegalArgumentException e) {
                output.commandInputError();
            }
        }
    }
}
