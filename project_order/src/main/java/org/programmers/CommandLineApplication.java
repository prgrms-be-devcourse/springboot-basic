package org.programmers;

import org.programmers.customer.CustomerService;
import org.programmers.voucher.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CommandLineApplication implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(CommandLineApplication.class);

    private final VoucherService voucherService;
    private final CustomerService customerService;

    public CommandLineApplication(VoucherService voucherService, CustomerService customerService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @Override
    public void run() {
        Console console = new Console();

        while (true) {
            try {
                console.printPrompt();

                String inputString = console.input("> ");
                if (inputString.equals("create")) {
                    console.printVoucherTypes();

                    String voucherType = console.input("> ");
                    if (voucherType.equals("f")) {
                        console.askAmount();

                        long amount = Long.parseLong(console.input("> "));

                        voucherService.createVoucher(VoucherType.FIXED, amount);
                    } else if (voucherType.equals("p")) {
                        console.askPercentage();

                        long percentage = Long.parseLong(console.input("> "));

                        voucherService.createVoucher(VoucherType.PERCENT, percentage);
                    }
                } else if (inputString.equals("list")) {
                    System.out.println(voucherService.getAllVouchers().toString());
                } else if ("blacklist".equals(inputString)) {
                    System.out.println(customerService.getAllCustomersOnBlacklist().toString());
                } else if (inputString.equals("exit")) {
                    break;
                }
            } catch (IOException e) {
                log.error("Got IOException", e);
            }
        }
    }

}
