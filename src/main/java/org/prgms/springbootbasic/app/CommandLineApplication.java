package org.prgms.springbootbasic.app;

import org.prgms.springbootbasic.cli.Command;
import org.prgms.springbootbasic.cli.CommandLine;
import org.prgms.springbootbasic.service.BlacklistedCustomerService;
import org.prgms.springbootbasic.service.VoucherService;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication {

    private final CommandLine commandLine;
    private final VoucherService voucherService;
    private final BlacklistedCustomerService blacklistedCustomerService;

    public CommandLineApplication(CommandLine commandLine, VoucherService voucherService, BlacklistedCustomerService blacklistedCustomerService) {
        this.commandLine = commandLine;
        this.voucherService = voucherService;
        this.blacklistedCustomerService = blacklistedCustomerService;
    }

    public void run(boolean isAppRunning) {

        while (isAppRunning) {
            Command command = Command.findCommand(commandLine.getMainCommand());

            switch (command) {
                case EXIT -> isAppRunning = !this.commandLine.stopCommandLine();
                case CREATE -> voucherService.createVoucher(commandLine.getVoucherCommand());
                case LIST -> commandLine.printList(voucherService.findAll());
                case BLACKLIST -> commandLine.printList(blacklistedCustomerService.findAll());
            }
        }
    }


}
