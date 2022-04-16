package org.prgrms.kdt;

import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherType;
import org.prgrms.kdt.service.customer.BlackListCustomerService;
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
        BlackListCustomerService blackListCustomerService = applicationContext.getBean(BlackListCustomerService.class);

        boolean isRunning = true;

        while (isRunning) {
            output.printCommandManual();

            String command = input.input();
            switch (CommandType.getCommandType(command)) {
                case EXIT:
                    isRunning = false;
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
                    output.printMessage(
                        voucherService.findAll().stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(",\n"))
                    );
                    break;
                case BLACK_LIST:
                    output.printMessage(
                        blackListCustomerService.findAll().stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(",\n"))
                    );
                    break;
                default:
                    output.printInvalidCommand();
                    break;
            }
        }

    }
}
