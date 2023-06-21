package com.dev.voucherproject.controller.menus;

import com.dev.voucherproject.model.Menu;
import com.dev.voucherproject.service.VoucherService;
import com.dev.voucherproject.view.Console;

public abstract class SelectMenuExecutor {
    private final Menu menu;
    protected final VoucherService voucherService;
    protected final Console console;

    public SelectMenuExecutor(Menu menu, VoucherService voucherService, Console console) {
        this.menu = menu;
        this.voucherService = voucherService;
        this.console = console;
    }

    public boolean isSatisfiedBy(Menu menu) {
        return this.menu == menu;
    }
    public abstract void execute(Menu menu);
}
