package com.prgrms.management.command.service;

import com.prgrms.management.command.domain.Command;
import com.prgrms.management.command.io.Input;
import com.prgrms.management.command.io.Output;
import com.prgrms.management.customer.service.CustomerService;
import com.prgrms.management.voucher.domain.VoucherType;
import com.prgrms.management.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandService implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(CommandService.class);
    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public CommandService(Input input, Output output, VoucherService voucherService, CustomerService customerService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @Override
    public void run(String... args) {
        while (true) {
            System.out.print(output.printCommand());
            try {
                String inputCommand = input.readLine();
                Command parse = Command.of(inputCommand);
                logger.info("SELECT {}", parse);
                chooseMenu(parse);
            } catch (RuntimeException e) {
                logger.warn("{}:{}",e.getClass(),e.getMessage());
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
                voucherService.createVoucher(voucherType, voucherType.toLong(inputAmount));
                break;
            case LIST:
                output.printResult(voucherService.findAll().toString());
                break;
            case BLACKLIST:
                output.printResult(customerService.findBlackList().toString());
                break;
            case EXIT:
                System.exit(0);
        }
    }
}
