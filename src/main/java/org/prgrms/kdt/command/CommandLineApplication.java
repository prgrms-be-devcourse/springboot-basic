package org.prgrms.kdt.command;

import org.prgrms.kdt.command.io.Console;
import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.context.ApplicationContext;

public class CommandLineApplication implements Runnable {

    private static final String INPUT_PROMPT = "> ";

    private final Input input;
    private final Output output;
    private final ApplicationContext applicationContext;
    private final VoucherService voucherService;

    public CommandLineApplication(Console console, ApplicationContext applicationContext) {
        this.input = console;
        this.output = console;
        this.applicationContext = applicationContext;
        this.voucherService = this.applicationContext.getBean(VoucherService.class);
    }

    @Override
    public void run() {
        while (true) {
            output.printOnStart(); // command 설명
            if (!execute())
                break;
        }
    }

    private boolean execute() {
        String inputCommandType = input.inputString(INPUT_PROMPT);

        CommandType command = CommandType.findCommand(inputCommandType);

        return command.execute(input, output, voucherService);
    }
}
