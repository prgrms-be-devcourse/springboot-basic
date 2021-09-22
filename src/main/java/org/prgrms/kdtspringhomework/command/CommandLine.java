package org.prgrms.kdtspringhomework.command;

import org.prgrms.kdtspringhomework.io.Input;
import org.prgrms.kdtspringhomework.io.InputConsole;
import org.prgrms.kdtspringhomework.io.Output;
import org.prgrms.kdtspringhomework.io.OutputConsole;
import org.prgrms.kdtspringhomework.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class CommandLine implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(CommandLine.class);

    private final Input input;
    private final Output output;
    private final ApplicationContext applicationContext;

    public CommandLine(InputConsole inputConsole, OutputConsole outputConsole, ApplicationContext applicationContext) {
        this.input = inputConsole;
        this.output = outputConsole;
        this.applicationContext = applicationContext;
    }

    @Override
    public void run() {
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);

        //프로그램 시작
        while (selectMenu(voucherService));
    }

    private boolean selectMenu(VoucherService voucherService) {
        boolean flag = true;

        output.start();
        output.inputCommandTypeMessage();
        try {
            String userCommand = input.receiveUserInput();
            flag = CommandStatus.execute(input, output, voucherService, userCommand);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            output.invalidCommandType();
        }
        return flag;
    }
}
