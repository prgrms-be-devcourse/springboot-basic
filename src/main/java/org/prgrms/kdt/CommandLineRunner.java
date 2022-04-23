package org.prgrms.kdt;

import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.model.customer.CustomerGrade;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherType;
import org.prgrms.kdt.service.customer.CustomerService;
import org.prgrms.kdt.service.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.UUID;
import java.util.stream.Collectors;

public class CommandLineRunner implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineRunner.class);

    private final ApplicationContext applicationContext;
    private final Input input;
    private final Output output;

    public CommandLineRunner(ApplicationContext applicationContext, Input input, Output output) {
        this.applicationContext = applicationContext;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
        CustomerService customerService = applicationContext.getBean(CustomerService.class);

        boolean isRunning = true;

        String command;
        CommandType commandType = CommandType.INVALID;
        while (commandType != CommandType.EXIT) {
            output.printCommandManual();

            command = input.input();
            commandType = CommandType.getCommandType(command);
            switch (commandType) {
                case EXIT:
                    commandType = CommandType.EXIT;
                    output.printShutDownSystem();
                    break;
                case CREATE:
                    output.printVoucherManual();

                    try {
                        VoucherType voucherType = VoucherType.getVoucherType(input.input());

                        output.printVoucherValue(voucherType);
                        long voucherValue = input.inputLong();

                        Voucher voucher = voucherService.createVoucher(UUID.randomUUID(), voucherValue, voucherType);
                        output.printVoucherCreateSuccess(voucher.toString());
                    } catch (IllegalArgumentException e) {
                        logger.warn("[Voucher] create error: {}", e.getMessage(), e);
                        output.printMessage(e.getMessage());
                    }

                    break;
                case LIST:
                    String vouchers = voucherService.findAll().stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(",\n"));
                    output.printMessage(vouchers);
                    break;
                case BLACK_LIST:
                    String blackListCustomers = customerService.findAllBlackList(CustomerGrade.BLACK_LIST).stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(",\n"));
                    output.printMessage(blackListCustomers);
                    break;
                default:
                    output.printInvalidCommand();
                    break;
            }
        }

    }
}
