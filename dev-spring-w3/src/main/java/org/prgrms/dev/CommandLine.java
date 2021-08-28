package org.prgrms.dev;

import org.prgrms.dev.command.CommandType;
import org.prgrms.dev.io.Input;
import org.prgrms.dev.io.InputConsole;
import org.prgrms.dev.io.Output;
import org.prgrms.dev.io.OutputConsole;
import org.prgrms.dev.voucher.service.VoucherService;
import org.springframework.context.ApplicationContext;

public class CommandLine implements Runnable {
    private final Input input;
    private final Output output;
    private final ApplicationContext applicationContext;

    public CommandLine(InputConsole input, OutputConsole output, ApplicationContext applicationContext) {
        this.input = input;
        this.output = output;
        this.applicationContext = applicationContext;
    }

    @Override
    public void run() {
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
        while (execute(voucherService));
    }

    public boolean execute(VoucherService voucherService){
        output.init();
        String inputCommandType = input.input("> ");
        System.out.println(inputCommandType);
        return CommandType.execute(inputCommandType, input, output, voucherService);
    }
}
