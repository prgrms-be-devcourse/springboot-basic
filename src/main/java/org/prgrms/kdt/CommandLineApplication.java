package org.prgrms.kdt;

import java.util.UUID;
import org.prgrms.kdt.io.Console;
import org.prgrms.kdt.io.command.Command;
import org.prgrms.kdt.io.command.Create;
import org.prgrms.kdt.io.command.Exit;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.VoucherService;
import org.prgrms.kdt.voucher.VoucherType;

/**
 * Created by yhh1056
 * Date: 2021/08/18 Time: 12:34 오전
 */
public class CommandLineApplication implements Runnable {

    private final VoucherService voucherService;
    private final Console console;
    private Command command;

    public CommandLineApplication(Console console, VoucherService voucherService) {
        this.voucherService = voucherService;
        this.console = console;
    }

    @Override
    public void run() {
        console.guide();

        while (true) {
            runConsoleApp();
        }
    }

    private void runConsoleApp() {
        switch (console.inputCommand()) {
            case EXIT -> new Exit().execute();
            case CREATE -> inputVoucher();
            case LIST -> console.vouchers(voucherService.getAllVoucher());
            case ERROR -> console.commandError();
        }
    }

    private void inputVoucher() {
        String[] split = console.inputVoucher().split(",");
        String voucherNumber = split[0].trim();
        Long rate = Long.parseLong(split[1].trim());

        insertVoucher(voucherNumber, rate);
    }

    private void insertVoucher(String voucherNumber, Long rate) {
        VoucherType voucherType = VoucherType.findByNumber(voucherNumber);
        switch (voucherType) {
            case FIX -> command = new Create(new FixedAmountVoucher(UUID.randomUUID(), rate), voucherService);
            case PERCENT -> command = new Create(new PercentDiscountVoucher(UUID.randomUUID(), rate), voucherService);
        }

        command.execute();
        console.successCreate();
    }

}
