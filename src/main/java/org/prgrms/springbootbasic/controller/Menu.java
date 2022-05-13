package org.prgrms.springbootbasic.controller;

import java.util.function.Function;
import org.prgrms.springbootbasic.controller.console.VoucherController;

public enum Menu {

    EXIT(voucherController -> false),
    CREATE(voucherController -> {
        voucherController.createVoucher();
        return true;
    }),
    LIST(voucherController -> {
        voucherController.printList();
        return true;
    }),
    BLACKLIST(voucherController -> {
        voucherController.printBlackList();
        return true;
    }),
    CREATE_CUSTOMER(voucherController -> {
        voucherController.createCustomer();
        return true;
    }),
    LIST_CUSTOMER(voucherController -> {
        voucherController.printAllCustomers();
        return true;
    }),
    ASSIGN_VOUCHER(voucherController -> {
        voucherController.assignVoucher();
        return true;
    }),
    LIST_CUSTOMER_VOUCHER(voucherController -> {
        voucherController.listCustomerVoucher();
        return true;
    }),
    DELETE_CUSTOMER_VOUCHER(voucherController -> {
        voucherController.deleteCustomerVoucher();
        return true;
    }),
    LIST_CUSTOMER_HAVING_SPECIFIC_VOUCHER_TYPE(voucherController -> {
        voucherController.listCustomerHavingSpecificVoucherType();
        return true;
    });

    private final Function<VoucherController, Boolean> process;

    Menu(Function<VoucherController, Boolean> process) {
        this.process = process;
    }

    public boolean apply(VoucherController voucherController) {
        return process.apply(voucherController);
    }
}
