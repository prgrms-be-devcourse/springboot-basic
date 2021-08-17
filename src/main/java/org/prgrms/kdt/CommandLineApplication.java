package org.prgrms.kdt;

import java.util.UUID;
import org.prgrms.kdt.config.MissionConfiguration;
import org.prgrms.kdt.io.Console;
import org.prgrms.kdt.io.command.Command;
import org.prgrms.kdt.io.command.Create;
import org.prgrms.kdt.io.command.Exit;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.VoucherService;
import org.prgrms.kdt.voucher.VoucherType;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by yhh1056
 * Date: 2021/08/18 Time: 12:34 오전
 */
public class CommandLineApplication implements Runnable {

    private final Console console;
    private Command command;

    public CommandLineApplication(Console console) {
        this.console = console;
    }

    @Override
    public void run() {
        VoucherService voucherService = getVoucherService(getApplicationContext());
        console.guide();

        while (true) {
            switch (console.inputCommand()) {
                case EXIT -> command = new Exit();
                case CREATE -> inputVoucher();
            }

            command.execute();
        }
    }

    private void inputVoucher() {
        String[] split = console.inputVoucher().split(",");
        String voucherNumber = split[0].trim();
        Long rate = Long.parseLong(split[1].trim());

        VoucherType voucherType = VoucherType.findByNumber(voucherNumber);
        switch (voucherType) {
            case FIX -> command = new Create(new FixedAmountVoucher(UUID.randomUUID(), rate));
            case PERCENT -> command = new Create(new PercentDiscountVoucher(UUID.randomUUID(), rate));
        }

        console.successCreate();
    }

    private AnnotationConfigApplicationContext getApplicationContext() {
        return new AnnotationConfigApplicationContext(MissionConfiguration.class);
    }

    private VoucherService getVoucherService(AnnotationConfigApplicationContext applicationContext) {
        return applicationContext.getBean(VoucherService.class);
    }
}
