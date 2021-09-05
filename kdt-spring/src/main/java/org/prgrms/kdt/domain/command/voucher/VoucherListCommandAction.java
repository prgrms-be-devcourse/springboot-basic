package org.prgrms.kdt.domain.command.voucher;

import org.prgrms.kdt.domain.command.CommandAction;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.io.IO;
import org.prgrms.kdt.service.VoucherService;

import java.io.IOException;


public class VoucherListCommandAction implements CommandAction {

    private final VoucherService voucherService;
    private final IO io;

    public VoucherListCommandAction(VoucherService voucherService, IO io) {
        this.voucherService = voucherService;
        this.io = io;
    }

    @Override
    public void action() throws IOException{
        printVouchers();
    }

    private void printVouchers() throws IOException {
        for (Voucher voucher : voucherService.vouchers()) {
            printVoucher(voucher);
        }
    }

    private void printVoucher(Voucher v) throws IOException {
        printVoucher(v.toString());
    }

    private void printVoucher(String voucher) throws IOException {
        io.writeLine(voucher);
    }
}
