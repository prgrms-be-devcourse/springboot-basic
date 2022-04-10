package com.prgms.management.command;

import com.prgms.management.command.io.CommandType;
import com.prgms.management.command.io.Console;
import com.prgms.management.customer.service.CustomerService;
import com.prgms.management.voucher.entity.Voucher;
import com.prgms.management.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication implements Runnable {
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final Console console;

    public CommandLineApplication(VoucherService voucherService, CustomerService customerService, Console console) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.console = console;
    }

    @Override
    public void run() {
        boolean flag = true;
        while (flag) {
            try {
                CommandType command = console.getCommandType();
                switch (command) {
                    case EXIT:
                        flag = false;
                        console.close();
                        break;
                    case LIST:
                        console.printListVoucher(voucherService.getAllVouchers());
                        break;
                    case CREATE:
                        Voucher voucher = voucherService.saveVoucher(console.getVoucher());
                        console.printOneVoucher(voucher);
                        break;
                    case BLACKLIST:
                        console.printListCustomer(customerService.getAllCustomers());
                        break;
                }
            } catch (Exception e) {
                console.printString(e.getMessage());
            }
            console.printString("");
        }
    }
}
