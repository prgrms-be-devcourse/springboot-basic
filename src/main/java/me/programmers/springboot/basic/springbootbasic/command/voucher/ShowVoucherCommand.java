package me.programmers.springboot.basic.springbootbasic.command.voucher;

import me.programmers.springboot.basic.springbootbasic.command.CommandStrategy;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleOutput;
import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import me.programmers.springboot.basic.springbootbasic.voucher.service.JdbcVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShowVoucherCommand implements CommandStrategy {

    private final JdbcVoucherService voucherService;

    @Autowired
    private ConsoleOutput console;

    public ShowVoucherCommand(JdbcVoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void operateCommand() {
        List<Voucher> vouchers = voucherService.getAllVouchers();

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
