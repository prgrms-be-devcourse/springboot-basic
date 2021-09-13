package org.programmers;

import org.programmers.customer.Customer;
import org.programmers.customer.CustomerService;
import org.programmers.voucher.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
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
                    case LIST -> console.printVoucherList(voucherService.getAllVouchers());
                    case BLACKLIST -> console.printBlackList(customerService.getBlackCustomers());
                    case WALLET -> {
                        List<Customer> customerList = customerService.getAllCustomers();

                        console.printCustomerList(customerList);
                        console.askCustomerNumber();
                        console.printSign();

                        int indexCustomer = Integer.parseInt(console.input());
                        List<Voucher> vouchersByOwnerId = voucherService.getVouchersByOwnerId(customerList.get(indexCustomer).getCustomerId());
                        if (vouchersByOwnerId.isEmpty()) {
                            System.out.println("There's no voucher for " + customerList.get(indexCustomer).getName() + ".");
                            System.out.println("Type yes to assign the voucher to " + customerList.get(indexCustomer).getName() + ".");
                            System.out.println("If not, type no.");

                            if(console.input().equals("yes")){
                                List<Voucher> voucherList = voucherService.getAllVouchers();

                                console.printVoucherList(voucherList);
                                console.askVoucherNumber();
                                console.printSign();

                                int indexVoucher = Integer.parseInt(console.input());
                                voucherService.assignToCustomer(customerList.get(indexCustomer), voucherList.get(indexVoucher));
                            }
                        }
                        else console.printVoucherList(vouchersByOwnerId);
                    }
                    case EXIT -> run = false;
                }
            } catch (IOException e) {
                log.error("Got IOException", e);
            }
        }
    }

}
