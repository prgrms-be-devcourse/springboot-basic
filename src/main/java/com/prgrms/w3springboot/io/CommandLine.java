package com.prgrms.w3springboot.io;

import com.prgrms.w3springboot.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommandLine implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(CommandLine.class);

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;

    public CommandLine(final Input input, final Output output, final VoucherService voucherService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
    }

    @Override
    public void run() {
        boolean flag = true;
        while (flag) {
            output.printInit();
            String commandType = input.input();
            try {
                flag = CommandType.of(commandType).execute(input, output, voucherService);
            } catch (Exception e) {
                output.printInvalidMessage();
                logger.error("{} - input : {}", e.getMessage(), commandType);
            }
        }
    }
}
