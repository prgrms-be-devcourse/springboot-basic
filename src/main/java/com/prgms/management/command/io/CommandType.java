package com.prgms.management.command.io;

import com.prgms.management.customer.service.BlackCustomerService;
import com.prgms.management.voucher.model.Voucher;
import com.prgms.management.voucher.service.VoucherService;

public enum CommandType {
    CREATE("create", "to create a new voucher.") {
        @Override
        public void execute(VoucherService voucherService, BlackCustomerService customerService, Console console) {
            Voucher voucher = voucherService.saveVoucher(console.getVoucher());
            console.printOneVoucher(voucher);
        }
    },
    LIST("list", "to list all vouchers.") {
        @Override
        public void execute(VoucherService voucherService, BlackCustomerService customerService, Console console) {
            console.printListVoucher(voucherService.getAllVouchers());
        }
    },
    EXIT("exit", "to exit the program.") {
        @Override
        public void execute(VoucherService voucherService, BlackCustomerService customerService, Console console) {
            console.close();
            System.exit(0);
        }
    },
    BLACKLIST("blacklist", "to list all black customers.") {
        @Override
        public void execute(VoucherService voucherService, BlackCustomerService customerService, Console console) {
            console.printListCustomer(customerService.getAllCustomers());
        }
    },
    ERROR("error", "this is error command.") {
        @Override
        public void execute(VoucherService voucherService, BlackCustomerService customerService, Console console) {
            console.printString("잘못된 명령어를 입력하셨습니다.");
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
                return ERROR;
        }
    }
    
    public String getConsoleScript() {
        return "Type **" + command + "** " + description;
    }
    
    public abstract void execute(VoucherService voucherService, BlackCustomerService customerService, Console console);
}
