package com.prgms.springbootbasic.controller;

import com.prgms.springbootbasic.model.Voucher;
import com.prgms.springbootbasic.model.VouchersInFile;
import com.prgms.springbootbasic.model.VouchersStorage;
import com.prgms.springbootbasic.ui.Console;
import com.prgms.springbootbasic.util.VoucherType;

import java.io.IOException;

public class VoucherCreateController implements VoucherController {

    private final Console console;

    public VoucherCreateController(Console console) {
        this.console = console;
    }

    @Override
    public boolean run() throws IOException {
        VouchersStorage vouchersStorage = new VouchersInFile();
        Voucher voucher = createVoucher();
        vouchersStorage.save(voucher);
        return true;
    }

    private Voucher createVoucher() {
        String voucherTypeOfString = console.inputVoucherType();
        VoucherType voucherType = VoucherType.of(voucherTypeOfString);
        Long number = console.inputVoucherNumber();
        return voucherType.createVoucher(number);
    }

}
