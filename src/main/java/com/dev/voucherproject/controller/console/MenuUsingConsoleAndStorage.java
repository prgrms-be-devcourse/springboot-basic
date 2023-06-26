package com.dev.voucherproject.controller.console;

import com.dev.voucherproject.model.storage.voucher.VoucherStorage;
import com.dev.voucherproject.view.Console;

public abstract class MenuUsingConsoleAndStorage implements MenuController {
    protected final VoucherStorage voucherStorage;
    protected final Console console;

    protected MenuUsingConsoleAndStorage(VoucherStorage voucherStorage, Console console) {
        this.voucherStorage = voucherStorage;
        this.console = console;
    }
}

