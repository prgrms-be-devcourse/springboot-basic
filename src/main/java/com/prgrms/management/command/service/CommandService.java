package com.prgrms.management.command.service;

import com.prgrms.management.command.domain.Command;
import com.prgrms.management.command.domain.GuideMessageType;
import com.prgrms.management.command.io.Input;
import com.prgrms.management.command.io.Output;
import com.prgrms.management.customer.service.CustomerService;
import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.domain.VoucherType;
import com.prgrms.management.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandService {
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

    public void run() {
        while (true) {
            output.print(GuideMessageType.COMMAND.getGUIDE());
            try {
                String inputCommand = input.readLine();
                Command parse = Command.of(inputCommand);
                excute(parse);
            } catch (RuntimeException e) {
                logger.warn("{}:{}",e.getClass(),e.getMessage());
            }
        }
    }

    private void excute(Command command) {
        switch (command) {
            case CREATE:
                output.print(GuideMessageType.VOUCHER.getGUIDE());
                String inputVoucherType = input.readLine();
                output.print(GuideMessageType.DISCOUNT.getGUIDE());
                String inputAmount = input.readLine();
                VoucherType voucherType = VoucherType.of(inputVoucherType);
                voucherService.createVoucher(voucherType, voucherType.toLong(inputAmount));
                break;
            case LIST:
                output.print(voucherService.findAll());
                break;
            case BLACKLIST:
                output.print(customerService.findBlackList().toString());
                break;
            case EXIT:
                System.exit(0);
        }
    }
}
