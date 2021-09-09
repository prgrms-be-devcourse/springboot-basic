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

    public CommandLine(Console console, ApplicationContext applicationContext) {
        this.input = console;
        this.output = console;
        this.applicationContext = applicationContext;
    }

    @Override
    public void run() {
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);

        //프로그램 시작
        while (selectMenu(voucherService)) ;
    }

    private boolean selectMenu(VoucherService voucherService) {
        boolean flag = true;

        output.start();
        output.inputCommandTypeMessage();
        try {
            String userCommand = input.receiveUserInput();
            flag = CommandStatus.execute(input, output, voucherService, userCommand);
        } catch (IllegalArgumentException e) {
            output.invalidCommandType();
        }
        return flag;
    }
}
