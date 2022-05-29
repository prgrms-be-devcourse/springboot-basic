package me.programmers.springboot.basic.springbootbasic.command.voucher;

import me.programmers.springboot.basic.springbootbasic.command.CommandStrategy;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleInput;
import me.programmers.springboot.basic.springbootbasic.voucher.VoucherType;
import me.programmers.springboot.basic.springbootbasic.voucher.model.FixedAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.PercentAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import me.programmers.springboot.basic.springbootbasic.voucher.service.JdbcVoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UpdateVoucherCommand implements CommandStrategy {
    private static final Logger logger = LoggerFactory.getLogger(UpdateVoucherCommand.class);

    private final JdbcVoucherService voucherService;

    private final ConsoleInput consoleInput;

    public UpdateVoucherCommand(JdbcVoucherService voucherService, ConsoleInput consoleInput) {
        this.voucherService = voucherService;
        this.consoleInput = consoleInput;
    }

    @Override
    public void operateCommand() {
        UUID uuid = UUID.fromString(consoleInput.inputCommand("update 할 Voucher uuid 입력 "));
        String type = consoleInput.inputCommand("type fixed or percent ");

        try {
            VoucherType voucherType = VoucherType.getVoucherStatus(type);
            Voucher voucher = createVoucher(uuid, voucherType);
            voucherService.update(voucher);
        } catch (IllegalArgumentException e) {
            logger.error("잘못된 바우처 타입 입력 {}", type);
        }
    }

    private Voucher createVoucher(UUID uuid, VoucherType voucherType) {
        Voucher voucher = null;
        switch (voucherType) {
            case FIXED:
                long amount = Long.parseLong(consoleInput.inputCommand("고정 할인 금액 입력 "));
                voucher = new FixedAmountVoucher(uuid, amount);
                break;
            case PERCENT:
                long percent = Long.parseLong(consoleInput.inputCommand("고정 할인 금액 입력 "));
                voucher = new PercentAmountVoucher(uuid, percent);
                break;
            default:
                break;
        }
        return voucher;
    }
}
