package com.prgrms.management.command;

import com.prgrms.management.command.io.Input;
import com.prgrms.management.command.io.Output;
import com.prgrms.management.voucher.domain.VoucherType;
import com.prgrms.management.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Console implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Console.class);
    private final Input input;
    private final Output output;
    private final VoucherService voucherService;

    public Console(Input input, Output output, VoucherService voucherService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
    }

    @Override
    public void run() {
        while(true) {
            System.out.print(output.printCommand());
            try {
                String inputCommand = input.readLine();
                Command parse = Command.of(inputCommand);
                logger.info("SELECT {}", parse);
                chooseMenu(parse);
            } catch (RuntimeException e) {
                logger.error("{}: {}",e.getClass(),e.getMessage());
            }
        }
    }

    private void chooseMenu(Command parse) {
        switch (parse) {
            case CREATE:
                System.out.print(output.printVoucher());
                String inputVoucher = input.readLine();
                VoucherType voucherType = VoucherType.of(inputVoucher);
                logger.info("SELECT {}", voucherType);
                System.out.print(voucherType.getINTRO());
                String inputAmount = input.readLine();
                voucherType.isValid(inputAmount);
                voucherService.createVoucher(voucherType,);
                break;
            case LIST:
                System.out.println("list = ");
                break;
            case EXIT:
                System.exit(0);
        }
    }
}
