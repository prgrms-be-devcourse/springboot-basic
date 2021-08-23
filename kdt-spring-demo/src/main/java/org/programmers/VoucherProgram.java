package org.programmers;

import org.programmers.console.Console;
import org.programmers.console.InputType;
import org.programmers.customer.service.CustomerService;
import org.programmers.voucher.model.VoucherType;
import org.programmers.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import java.io.IOException;
import java.util.UUID;

public class VoucherProgram implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(VoucherProgram.class);

    private final VoucherService voucherService;
    private final Console console;
    private final CustomerService customerService;

    public VoucherProgram(VoucherService voucherService, Console console, CustomerService customerService) {
        this.voucherService = voucherService;
        this.console = console;
        this.customerService = customerService;
    }

    public void voucherProgramRun() throws IOException {
        while (true) {
            console.initInfoOutput();
            String initInput = console.initInput();
            InputType inputType = InputType.getInputType(initInput);

            switch (inputType) {
                case EXIT -> {
                    console.exitOutput();
                    System.exit(0);
                }

                case CREATE -> {
                    console.createTypeInfoOutput();
                    String voucherType = console.getVoucherType();
                    VoucherType inputVoucherType = VoucherType.getInputType(voucherType);

                    console.createNumberInfoOutput();
                    long number = Long.parseLong(String.valueOf(console.getVoucherNumber()));

                    voucherService.createVoucher(UUID.randomUUID(), inputVoucherType, number);
                }

                case VOUCHERLIST -> {
                    console.listInfoOutput();
                    console.printVoucherList(voucherService.getVoucherList());
                }

                case BLACKLIST -> {
                    console.blackListInfoOutput();
                    console.printBlackList(customerService.getBlackList());
                }
                default -> console.showInputError();
            }
        }
    }

    @Override
    public void run(String... args) throws IOException {
        voucherProgramRun();
    }
}
