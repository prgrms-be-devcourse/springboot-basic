package org.prgrms.kdt.presentation.controller;

import org.prgrms.kdt.dao.entity.customer.Customer;
import org.prgrms.kdt.dao.entity.voucher.Voucher;
import org.prgrms.kdt.presentation.io.ConsoleIO;
import org.prgrms.kdt.service.CustomerService;
import org.prgrms.kdt.service.VoucherService;

import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CommandType {
    EXIT("exit", "Type 'exit' to exit the program.") {
        @Override
        public boolean executeCommand(ConsoleIO consoleIO, VoucherService voucherService, CustomerService customerService) {
            consoleIO.terminate();
            return false;
        }
    },
    CREATE("create", "Type 'create' to create a new voucher.") {
        @Override
        public boolean executeCommand(ConsoleIO consoleIO, VoucherService voucherService, CustomerService customerService) {
            String voucherType = consoleIO.inputVoucherType();
            String voucherDiscountValue = consoleIO.inputVoucherDiscountValue();
            voucherService.create(voucherType, voucherDiscountValue);
            return true;
        }
    },
    ASSIGN("assign", "Type 'assign' to assign voucher into a certain customer.") {
        @Override
        public boolean executeCommand(ConsoleIO consoleIO, VoucherService voucherService, CustomerService customerService) {
            consoleIO.printItems(voucherService.getAllVouchers());
            String assignVoucherId = consoleIO.inputId(VOUCHER);
            Voucher findVoucher = voucherService.findVoucherById(assignVoucherId);

            consoleIO.printItems(customerService.getAllCustomers());
            String assignCustomerId = consoleIO.inputId(CUSTOMER);
            Customer findCustomer = customerService.findCustomerById(assignCustomerId);

            voucherService.assignVoucher(findVoucher, findCustomer);
            return true;
        }
    },
    LIST_VOUCHERS("list vouchers", "Type 'list vouchers' to list all vouchers.") {
        @Override
        public boolean executeCommand(ConsoleIO consoleIO, VoucherService voucherService, CustomerService customerService) {
            consoleIO.printItems(voucherService.getAllVouchers());
            return true;
        }
    },
    LIST_VOUCHER_WALLET("list voucher wallet", "Type 'list voucher wallet' to list all vouchers having certain customer") {
        @Override
        public boolean executeCommand(ConsoleIO consoleIO, VoucherService voucherService, CustomerService customerService) {
            consoleIO.printItems(customerService.getAllCustomers());
            String assignCustomerId = consoleIO.inputId(CUSTOMER);

            consoleIO.printItems(voucherService.getOwnedVouchers(assignCustomerId));
            return true;
        }
    },
    LIST_CUSTOMERS("list customers", "Type 'list customers' to list all customers.") {
        @Override
        public boolean executeCommand(ConsoleIO consoleIO, VoucherService voucherService, CustomerService customerService) {
            consoleIO.printItems(customerService.getAllCustomers());
            return true;
        }
    },
    LIST_CUSTOMER_HAVING_VOUCHER("list customer voucher", "Type 'list customer voucher' to find customer having certain voucher") {
        @Override
        public boolean executeCommand(ConsoleIO consoleIO, VoucherService voucherService, CustomerService customerService) {
            consoleIO.printItems(voucherService.getAllVouchers());
            String voucherId = consoleIO.inputId(VOUCHER);
            UUID ownedCustomerId = voucherService.findVoucherById(voucherId).getOwnedCustomerId()
                    .orElseThrow(() -> new IllegalArgumentException("해당 바우처은 고객에게 할당되지 않았습니다."));
            consoleIO.printItem(customerService.findCustomerById(ownedCustomerId.toString()));
            return true;
        }
    },
    REMOVE_CUSTOMER_HAVING_VOUCHER("remove customer voucher", "Type 'remove customer voucher' to remove customer's voucher") {
        @Override
        public boolean executeCommand(ConsoleIO consoleIO, VoucherService voucherService, CustomerService customerService) {
            consoleIO.printItems(customerService.getAllCustomers());
            String customerId = consoleIO.inputId(CUSTOMER);

            consoleIO.printItems(voucherService.getOwnedVouchers(customerId));
            String removeVoucherId = consoleIO.inputId(VOUCHER);
            voucherService.removeAssignment(removeVoucherId);
            return true;
        }
    },
    LIST_BLACKLIST("list blacklist", "Type 'list blacklist' to list all blacklist.") {
        @Override
        public boolean executeCommand(ConsoleIO consoleIO, VoucherService voucherService, CustomerService customerService) {
            consoleIO.printItems(customerService.getAllBlacklist());
            return true;
        }
    };

    private final static String VOUCHER = "Voucher";
    private final static String CUSTOMER = "Customer";
    public final String command;
    private final String expression;

    CommandType(String command, String expression) {
        this.command = command;
        this.expression = expression;
    }

    public static CommandType of(String cmd) {
        return Stream.of(values())
                .filter(cmdStatus -> cmdStatus.command.equals(cmd))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 Command입니다."));
    }

    public static String getAllCommandExpression() {
        return Stream.of(values())
                .map(cmdStat -> cmdStat.expression)
                .collect(Collectors.joining("\n"));
    }

    public abstract boolean executeCommand(ConsoleIO consoleIO, VoucherService voucherService, CustomerService customerService);
}
