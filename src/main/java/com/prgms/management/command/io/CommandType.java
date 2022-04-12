package com.prgms.management.command.io;

import com.prgms.management.customer.service.CustomerService;
import com.prgms.management.voucher.entity.Voucher;
import com.prgms.management.voucher.exception.VoucherException;
import com.prgms.management.voucher.service.VoucherService;

public enum CommandType {
    CREATE {
        @Override
        public void execute(VoucherService voucherService, CustomerService customerService, Console console) throws VoucherException {
            Voucher voucher = voucherService.saveVoucher(console.getVoucher());
            console.printOneVoucher(voucher);
        }
    },
    LIST {
        @Override
        public void execute(VoucherService voucherService, CustomerService customerService, Console console) throws VoucherException {
            console.printListVoucher(voucherService.getAllVouchers());
        }
    },
    EXIT {
        @Override
        public void execute(VoucherService voucherService, CustomerService customerService, Console console) {
            console.close();
            System.exit(0);
        }
    },
    BLACKLIST {
        @Override
        public void execute(VoucherService voucherService, CustomerService customerService, Console console) {
            console.printListCustomer(customerService.getAllCustomers());
        }
    },
    NONE {
        @Override
        public void execute(VoucherService voucherService, CustomerService customerService, Console console) {
            console.printString("잘못된 명령어를 입력하셨습니다.");
        }
    };

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
                return NONE;
        }
    }

    public abstract void execute(VoucherService voucherService, CustomerService customerService, Console console) throws VoucherException;
}
