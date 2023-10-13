package org.prgrms.kdtspringdemo.voucher.controller;

import org.prgrms.kdtspringdemo.view.InputConsole;
import org.prgrms.kdtspringdemo.view.OutputConsole;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.List;

public class VoucherController {
    private final VoucherService voucherService;
    private final InputConsole inputConsole;
    private final OutputConsole outputConsole;

    public VoucherController(VoucherService voucherService, InputConsole inputConsole, OutputConsole outputConsole) {
        this.voucherService = voucherService;
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
    }

    public void createVoucher() throws IOException {
        String voucherType = inputConsole.getString();
        UUID voucherId = UUID.randomUUID();
        long amount = Long.parseLong(inputConsole.getString());
        voucherService.createVoucher(voucherType, voucherId, amount);
    }

    public void showAllVouchers() {
        Map<UUID, Voucher> voucherMap = voucherService.getVoucherList();
        voucherMap.forEach((voucherId, voucher) -> outputConsole.printVoucher(voucher));
    }

    public void endProgram() {
        voucherService.endVoucherService();
    }


}
