package com.prgms.springbootbasic.controller;

import com.prgms.springbootbasic.model.Voucher;
import com.prgms.springbootbasic.model.VouchersInFile;
import com.prgms.springbootbasic.model.VouchersStorage;
import com.prgms.springbootbasic.ui.Console;

import java.io.IOException;
import java.util.List;

public class VoucherListController implements VoucherController {

    private final Console console;

    public VoucherListController(Console console) {
        this.console = console;
    }

    @Override
    public boolean run() throws IOException {
        VouchersStorage vouchersStorage = new VouchersInFile();
        List<Voucher> vouchers = vouchersStorage.findAll();
        console.showVoucherList(vouchers);
        return true;
    }

}
