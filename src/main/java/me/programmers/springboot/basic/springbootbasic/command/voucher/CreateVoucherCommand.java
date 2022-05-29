package me.programmers.springboot.basic.springbootbasic.command.voucher;

import me.programmers.springboot.basic.springbootbasic.command.CommandStrategy;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleInput;
import me.programmers.springboot.basic.springbootbasic.voucher.VoucherType;
import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import me.programmers.springboot.basic.springbootbasic.voucher.service.JdbcVoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CreateVoucherCommand implements CommandStrategy {

    private static final Logger logger = LoggerFactory.getLogger(CommandStrategy.class);

    private final JdbcVoucherService voucherService;
    private final ConsoleInput console;

    public CreateVoucherCommand(JdbcVoucherService voucherService, ConsoleInput console) {
        this.voucherService = voucherService;
        this.console = console;
    }

    @Override
    public void operateCommand() {
        String voucherTypeCommand = console.inputCommand("Type fixed or percent: ");
        try {
            VoucherType voucherType = VoucherType.getVoucherStatus(voucherTypeCommand);
            Voucher voucher = VoucherType.getSpecificVoucher(voucherType, console);
            voucherService.save(voucher);
        } catch (IllegalArgumentException e) {
            logger.error("잘못된 바우처 타입 입력 {}", voucherTypeCommand);
        }
    }
}
