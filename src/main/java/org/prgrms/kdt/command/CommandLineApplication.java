package org.prgrms.kdt.command;

import org.prgrms.kdt.command.io.Console;
import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

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
        voucherService = this.applicationContext.getBean(VoucherService.class);
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

        Optional<CommandType> command = CommandType.findCommand(inputCommandType); //.execute(input, output, voucherService);

        if (command.isEmpty()) { // TODO: Refactoring
            output.printInputError();
            return true;
        }

        return command.get().execute(input, output, voucherService);
    }
}
