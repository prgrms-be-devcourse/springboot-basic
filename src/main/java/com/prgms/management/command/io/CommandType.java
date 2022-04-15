package com.prgms.management.command.io;

import com.prgms.management.customer.service.CustomerService;
import com.prgms.management.voucher.entity.Voucher;
import com.prgms.management.voucher.exception.VoucherException;
import com.prgms.management.voucher.service.VoucherService;

public enum CommandType {
    CREATE("create", "to create a new voucher.") {
        @Override
        public void execute(VoucherService voucherService, CustomerService customerService, Console console) throws VoucherException {
            Voucher voucher = voucherService.saveVoucher(console.getVoucher());
            console.printOneVoucher(voucher);
        }
    },
    LIST("list", "to list all vouchers.") {
        @Override
        public void execute(VoucherService voucherService, CustomerService customerService, Console console) throws VoucherException {
            console.printListVoucher(voucherService.getAllVouchers());
        }
    },
    EXIT("exit", "to exit the program.") {
        @Override
        public void execute(VoucherService voucherService, CustomerService customerService, Console console) {
            console.close();
            System.exit(0);
        }
    },
    BLACKLIST("blacklist", "to list all black customers.") {
        @Override
        public void execute(VoucherService voucherService, CustomerService customerService, Console console) {
            console.printListCustomer(customerService.getAllCustomers());
        }
    };

    private final String command;
    private final String description;

    CommandType(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public static CommandType of(String command) {
        switch (command.toLowerCase()) {
            case "list":
                return LIST;
            case "create":
                return CREATE;
            case "exit":
                return EXIT;
            case "blacklist":
                return BLACKLIST;
            default:
                return null;
        }
    }

    public String getScript() {
        return "Type **" + command + "** " + description;
    }

    public abstract void execute(VoucherService voucherService, CustomerService customerService, Console console) throws VoucherException;
}
