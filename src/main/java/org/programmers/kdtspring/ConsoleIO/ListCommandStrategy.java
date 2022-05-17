package org.programmers.kdtspring.ConsoleIO;

import org.programmers.kdtspring.entity.voucher.Voucher;
import org.programmers.kdtspring.service.VoucherService;

import java.util.List;

public class ListCommandStrategy implements CommandStrategy {

    private final Output output;
    private final VoucherService voucherService;

    public ListCommandStrategy(Output output, VoucherService voucherService) {
        this.output = output;
        this.voucherService = voucherService;
    }

    @Override
    public void runCommand() {

        List<Voucher> vouchers = voucherService.getVouchers();

        if (vouchers.isEmpty()) {
            System.out.println("저장된 바우처가 없습니다.");
            return;
        }
        System.out.println("===== 바우처 리스트 ======");
        output.showAllVoucher();
    }
}