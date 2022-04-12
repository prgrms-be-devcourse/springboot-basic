package com.mountain.voucherApp.engine.list;

import com.mountain.voucherApp.io.Console;
import com.mountain.voucherApp.voucher.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultList implements ListStrategy {

    private final Console console;
    private final VoucherService voucherService;

    @Autowired
    public DefaultList(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    @Override
    public void list() {
        console.printAllList(voucherService.findAll());
    }
}
