package com.dev.voucherproject.controller.console;

import com.dev.voucherproject.model.menu.Menu;
import com.dev.voucherproject.model.voucher.VoucherDataAccessor;
import com.dev.voucherproject.view.Console;

public abstract class SelectMenuExecutor {
    private final Menu menu;
    protected final VoucherDataAccessor voucherDataAccessor;
    protected final Console console;

    public SelectMenuExecutor(Menu menu, VoucherDataAccessor voucherDataAccessor, Console console) {
        this.menu = menu;
        this.voucherDataAccessor = voucherDataAccessor;
        this.console = console;
    }

    public boolean isSatisfiedBy(Menu menu) {
        return this.menu == menu;
    }
    public abstract void execute(Menu menu);
}
