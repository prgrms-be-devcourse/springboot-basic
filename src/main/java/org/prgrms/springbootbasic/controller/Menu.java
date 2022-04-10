package org.prgrms.springbootbasic.controller;

import java.util.function.Function;

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
    });

    private final Function<VoucherController, Boolean> function;

    Menu(Function<VoucherController, Boolean> function) {
        this.function = function;
    }

    public boolean apply(VoucherController voucherController) {
        return function.apply(voucherController);
    }
}
