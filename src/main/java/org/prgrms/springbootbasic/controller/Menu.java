package org.prgrms.springbootbasic.controller;

import java.util.function.Function;

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
