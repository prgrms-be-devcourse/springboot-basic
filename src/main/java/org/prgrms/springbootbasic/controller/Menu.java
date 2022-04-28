package org.prgrms.springbootbasic.controller;

import java.util.function.Function;
import org.prgrms.springbootbasic.controller.console.VoucherController;

public enum Menu {

    EXIT("exit", voucherController -> false),
    CREATE("create", voucherController -> {
        voucherController.createVoucher();
        return true;
    }),
    LIST("list", voucherController -> {
        voucherController.printList();
        return true;
    }),
    BLACKLIST("blacklist", voucherController -> {
        voucherController.printBlackList();
        return true;
    }),
    CREATECUSTOMER("createCustomer", voucherController -> {
        voucherController.createCustomer();
        return true;
    }),
    LISTCUSTOMER("listCustomer", voucherController -> {
        voucherController.printAllCustomers();
        return true;
    }),
    ASSIGNVOUCHER("assignVoucher", voucherController -> {
        voucherController.assignVoucher();
        return true;
    }),
    LISTCUSTOMERVOUCHER("listCustomerVoucher", voucherController -> {
        voucherController.listCustomerVoucher();
        return true;
    }),
    DELETECUSTOMERVOUCHER("deleteCustomerVoucher", voucherController -> {
        voucherController.deleteCustomerVoucher();
        return true;
    }),
    LISTCUSTOMERHAVINGSEPCIFICVOUCHERTYPE("listCustomerHavingSpecificVoucherType",
        voucherController -> {
            voucherController.listCustomerHavingSpecificVoucherType();
            return true;
        });

    private final String textName;
    private final Function<VoucherController, Boolean> function;

    Menu(final String textName, Function<VoucherController, Boolean> function) {
        this.textName = textName;
        this.function = function;
    }

    public boolean apply(VoucherController voucherController) {
        return function.apply(voucherController);
    }
}
