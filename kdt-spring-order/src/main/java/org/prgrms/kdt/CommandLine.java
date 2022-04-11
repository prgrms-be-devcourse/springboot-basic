package org.prgrms.kdt;

import org.prgrms.kdt.blacklist.service.BlacklistService;
import org.prgrms.kdt.command.CmdList;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.InputConsole;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.io.OutputConsole;

import org.prgrms.kdt.voucher.service.MemoryVoucherService;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class CommandLine implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(CommandLine.class);
    private final ApplicationContext applicationContext;

    private final Input input;
    private final Output output;

    public CommandLine(ApplicationContext applicationContext, InputConsole input, OutputConsole output) {
        this.applicationContext = applicationContext;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        MemoryVoucherService memoryVoucherService = applicationContext.getBean(MemoryVoucherService.class);
        BlacklistService blacklistService = applicationContext.getBean(BlacklistService.class);
        blacklistService.setBlacklistRepository();

        while(true) {
            try { /* Voucher Application Running */
                output.init();
                String inputCommand = input.input("> ");
                if(!CmdList.execute(inputCommand, input, output, memoryVoucherService, blacklistService)) break;
            } catch(Exception e) {
                logger.error(e.getMessage());
                output.printInvalidCmd(e.getMessage());
            }
        }
    }

}
