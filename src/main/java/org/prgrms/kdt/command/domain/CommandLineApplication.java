package org.prgrms.kdt.command.domain;

import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.command.service.ExitService;
import org.prgrms.kdt.command.service.customer.CustomerCommandService;
import org.prgrms.kdt.command.service.voucher.VoucherCommandService;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication implements Runnable {
    private final ExitService exitService;
    private final CustomerCommandService customerService;
    private final VoucherCommandService voucherService;

    public CommandLineApplication(
            final ExitService exitService,
            final CustomerCommandService customerService,
            final VoucherCommandService voucherService) {
        this.exitService = exitService;
        this.customerService = customerService;
        this.voucherService = voucherService;
    }

    @Override
    public void run() {
        boolean programRunning = true;
        do {
            Output.commandChooseMessage();

            final String commandInput = Input.input();
            switch (commandInput) {
                case "exit" -> {
                    exitService.commandRun();
                    programRunning = false;
                }
                case "customer" -> customerService.commandRun();
                case "voucher" -> voucherService.commandRun();
                default -> Output.inputTypeErrorMessage(commandInput);
            }
        } while (programRunning);
    }
}
