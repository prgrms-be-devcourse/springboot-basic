package me.programmers.springboot.basic.springbootbasic.command.voucher;

import me.programmers.springboot.basic.springbootbasic.command.CommandStrategy;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleOutput;
import me.programmers.springboot.basic.springbootbasic.io.Out;
import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import me.programmers.springboot.basic.springbootbasic.voucher.service.JdbcVoucherService;

import java.util.List;

public class ShowVoucherCommand implements CommandStrategy {

    private final ConsoleOutput console = new Out();
    private final JdbcVoucherService voucherService;

    public ShowVoucherCommand(JdbcVoucherService voucherService) {
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
