package org.programmers;

import org.programmers.customer.CustomerService;
import org.programmers.voucher.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;

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

        boolean run = true;
        while (run) {
            try {
                console.printPrompt();

                String inputString = console.input("> ");
                switch (inputString) {
                    case "create" -> {
                        console.printVoucherTypes();

                        String voucherType = console.input("> ");
                        if (voucherType.equals("f")) {
                            console.askAmount();

                            long amount = Long.parseLong(console.input("> "));

                            voucherService.createVoucher(VoucherType.FIXED, UUID.randomUUID(), amount);
                        } else if (voucherType.equals("p")) {
                            console.askPercentage();

                            long percentage = Long.parseLong(console.input("> "));

                            voucherService.createVoucher(VoucherType.PERCENT, UUID.randomUUID(), percentage);
                        }
                    }
                    case "list" -> System.out.println(voucherService.getAllVouchers().toString());
                    case "blacklist" -> System.out.println(customerService.getBlackCustomers().toString());
                    case "exit" -> run = false;
                }
            } catch (IOException e) {
                log.error("Got IOException", e);
            }
        }
    }

}
