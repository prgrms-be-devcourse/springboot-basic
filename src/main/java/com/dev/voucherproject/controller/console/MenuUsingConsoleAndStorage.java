package com.dev.voucherproject.controller.console;

import com.dev.voucherproject.storage.VoucherStorage;
import com.dev.voucherproject.view.Console;

public abstract class MenuUsingConsoleAndStorage implements MenuController {
    protected final VoucherStorage voucherStorage;
    protected final Console console;

    public MenuUsingConsoleAndStorage(VoucherStorage voucherStorage, Console console) {
        this.voucherStorage = voucherStorage;
        this.console = console;
    }
}

