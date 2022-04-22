package me.programmers.springboot.basic.springbootbasic.command.voucher;

import me.programmers.springboot.basic.springbootbasic.command.CommandStrategy;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleInput;
import me.programmers.springboot.basic.springbootbasic.io.In;
import me.programmers.springboot.basic.springbootbasic.voucher.VoucherType;
import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import me.programmers.springboot.basic.springbootbasic.voucher.service.JdbcVoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateVoucherCommand implements CommandStrategy {

    private static final Logger logger = LoggerFactory.getLogger(CommandStrategy.class);
    private final ConsoleInput console = new In();
    private final JdbcVoucherService voucherService;

    public CreateVoucherCommand(JdbcVoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void operateCommand() {
        String voucherTypeCommand = console.inputCommand("Type fixed or percent: ");
        try {
            VoucherType voucherType = VoucherType.getVoucherStatus(voucherTypeCommand);
            Voucher voucher = VoucherType.getSpecificVoucher(voucherType);
            voucherService.save(voucher);
        } catch (IllegalArgumentException e) {
            logger.error("잘못된 바우처 타입 입력 {}", voucherTypeCommand);
        }
    }
}
