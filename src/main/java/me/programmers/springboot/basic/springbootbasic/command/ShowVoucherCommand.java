package me.programmers.springboot.basic.springbootbasic.command;

import me.programmers.springboot.basic.springbootbasic.io.Console;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleOutput;
import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import me.programmers.springboot.basic.springbootbasic.voucher.service.VoucherService;

import java.util.List;

public class ShowVoucherCommand implements CommandStrategy{

    private final ConsoleOutput console = new Console();
    private final VoucherService voucherService;

    public ShowVoucherCommand(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void operateCommand() {
        List<Voucher> vouchers = voucherService.getVouchers();

        if (vouchers.isEmpty()) {
            console.output("저장된 Voucher 가 없습니다.");
            return;
        }

        console.output("Voucher 리스트");
        for (Voucher voucher : vouchers) {
            console.output(voucher.toString());
        }
    }
}
