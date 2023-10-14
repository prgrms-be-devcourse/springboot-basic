package org.prgrms.kdtspringdemo.voucher.controller;

import org.prgrms.kdtspringdemo.view.InputConsole;
import org.prgrms.kdtspringdemo.view.OutputConsole;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherTypeFunction;
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

    public VoucherTypeFunction findVoucherType() throws IOException {
        outputConsole.getVoucherType();
        String voucherType = inputConsole.getString();
        return voucherService.getVoucherType(voucherType);
    }

    public void createVoucher() throws IOException {
        VoucherTypeFunction voucherType = findVoucherType();
        UUID voucherId = UUID.randomUUID();
        outputConsole.getVoucherAmount();
        try {
            long amount = Long.parseLong(inputConsole.getString());
            voucherService.createVoucher(voucherType, voucherId, amount);
        } catch (NumberFormatException e) {
            System.out.println("올바른 숫자 형식이 아닙니다.");
        }
    }

    public void showAllVouchers() {
        Map<UUID, Voucher> voucherMap = voucherService.getVoucherList();
        voucherMap.forEach((voucherId, voucher) -> outputConsole.printVoucher(voucher));
    }

    public void endProgram() {
        voucherService.endVoucherService();
    }


}
