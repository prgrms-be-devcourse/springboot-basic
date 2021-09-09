package org.programmers;

import org.programmers.customer.CustomerService;
import org.programmers.voucher.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Locale;
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
                console.printSign();

                CommandType command = CommandType.valueOf(console.input().toUpperCase(Locale.ROOT));
                switch (command) {
                    case CREATE -> {
                        console.printVoucherTypes();
                        console.printSign();

                        VoucherType voucherType = VoucherType.valueOf(console.input().toUpperCase(Locale.ROOT));
                        switch (voucherType) {
                            case FIXED -> console.askAmount();
                            case PERCENT -> console.askPercentage();
                        }

                        console.printSign();

                        long value = Long.parseLong(console.input());
                        voucherService.createVoucher(voucherType, UUID.randomUUID(), value);
                    }
                    case LIST -> System.out.println(voucherService.getAllVouchers().toString());
                    case BLACKLIST -> System.out.println(customerService.getBlackCustomers().toString());
                    case EXIT -> run = false;
                }
            } catch (IOException e) {
                log.error("Got IOException", e);
            }
        }
    }

}
