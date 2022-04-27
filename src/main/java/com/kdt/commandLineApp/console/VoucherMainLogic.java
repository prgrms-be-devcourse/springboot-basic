package com.kdt.commandLineApp.console;

import com.kdt.commandLineApp.io.IO;
import com.kdt.commandLineApp.service.voucher.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoucherMainLogic {
    private IO io;
    private VoucherService voucherService;

    @Autowired
    public VoucherMainLogic(VoucherService voucherService, IO io) {
        this.io = io;
        this.voucherService = voucherService;
    }

    public void createVoucher() throws Exception {
        io.print("Type voucher type(fixed or percent) and amount");
        String voucherType = io.get();
        int amount = Integer.parseInt(io.get());
        voucherService.addVoucher(voucherType, amount);
    }

    public void showVouchers() {
        io.print(voucherService.getVouchers(0, 0, null));
    }
}
