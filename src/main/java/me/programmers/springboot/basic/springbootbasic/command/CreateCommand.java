package me.programmers.springboot.basic.springbootbasic.command;

import me.programmers.springboot.basic.springbootbasic.io.Console;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleInput;
import me.programmers.springboot.basic.springbootbasic.voucher.VoucherType;
import me.programmers.springboot.basic.springbootbasic.voucher.model.FixedAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.PercentAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class CreateCommand implements CommandStrategy {

    private static final Logger logger = LoggerFactory.getLogger(CommandStrategy.class);
    private final ConsoleInput console = new Console();
    private final VoucherService voucherService;

    public CreateCommand(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void operateCommand() {
        String cmd = console.inputCommand("Type fixed or percent: ");

        try {
            VoucherType voucherStatus = VoucherType.getVoucherStatus(cmd);
            switch (voucherStatus) {
                case FIXED:
                    voucherService.save(new FixedAmountVoucher(UUID.randomUUID(), 100));
                    break;
                case PERCENT:
                    voucherService.save(new PercentAmountVoucher(UUID.randomUUID(), 50));
                    break;
                default:
                    logger.error("잘못된 바우처 타입 입력: {}", cmd);
                    break;
            }
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        }
    }
}
