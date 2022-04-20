package org.prgrms.kdt.model.function;

import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.InputConsole;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.io.OutputConsole;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.service.BlackListService;
import org.prgrms.kdt.service.CustomerService;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public enum Function {
    exit(" to exit the program") {
        @Override
        public boolean execute(VoucherService voucherService, BlackListService blackListService, CustomerService customerService) {
            getOutput().printExitMessage();
            return isEndProgram;
        }
    },
    create(" to create a new voucher") {
        @Override
        public boolean execute(VoucherService voucherService, BlackListService blackListService, CustomerService customerService) {
            getOutput().printVoucherType();
            Input input = new InputConsole();
            try {
                createVoucherByVoucherType(input.inputVoucherType(), input.inputAmount(), voucherService);
            } catch (IllegalArgumentException e) {
                logger.info("error -> {}", e.getMessage());
                getOutput().printExceptionMessage(e.getMessage());
            }
            return !isEndProgram;
        }
    },
    voucherList(" to list all vouchers") {
        @Override
        public boolean execute(VoucherService voucherService, BlackListService blackListService, CustomerService customerService) {
            printVoucherList(voucherService.getVoucherList());
            return !isEndProgram;
        }
    },
    blackList(" to list all blackList") {
        @Override
        public boolean execute(VoucherService voucherService, BlackListService blackListService, CustomerService customerService) {
            getOutput().printList(blackListService.getBlackList());
            return !isEndProgram;
        }
    },
    add("to create new customer") {
        @Override
        public boolean execute(VoucherService voucherService, BlackListService blackListService, CustomerService customerService) {
            Input input = new InputConsole();
            String name = input.inputCustomerName();
            String email = input.inputCustomerEmail();
            Customer customer = new Customer(UUID.randomUUID(), name, email, LocalDateTime.now(), LocalDateTime.now());
            customerService.join(customer);
            return !isEndProgram;
        }
    };

    private final String explain;

    private final static Logger logger = LoggerFactory.getLogger(Function.class);

    private final static boolean isEndProgram = true;

    Function(String explain) {
        this.explain = explain;
    }

    public String getExplain() {
        return explain;
    }

    public abstract boolean execute(VoucherService voucherService, BlackListService blackListService, CustomerService customerService);

    Output getOutput() {
        return new OutputConsole();
    }

    void createVoucherByVoucherType(String voucherType, String discountAmount, VoucherService voucherService) {
        voucherService.createVoucher(UUID.randomUUID(),
                Utility.toInt(voucherType),
                Utility.toInt(discountAmount));
    }

    void printVoucherList(Map<UUID, Voucher> voucherList) {
        if (voucherList.isEmpty()) {
            getOutput().printVoucherListEmptyError();
            return;
        }
        for (Map.Entry<UUID, Voucher> entry : voucherList.entrySet()) {
            Voucher voucher = entry.getValue();
            System.out.println(voucher.toString());
        }
    }

}
