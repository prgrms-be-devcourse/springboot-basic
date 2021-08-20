package org.prgrms.kdt;

import java.util.UUID;
import org.prgrms.kdt.command.ListCommand;
import org.prgrms.kdt.io.Console;
import org.prgrms.kdt.command.Command;
import org.prgrms.kdt.command.CreateCommand;
import org.prgrms.kdt.command.ExitCommand;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.VoucherData;
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
            case CREATE -> command = newCreateCommand(inputVoucher());
            case LIST -> command = new ListCommand(voucherService.getAllVoucher(), console);
            case EXIT -> command = new ExitCommand();
            case ERROR -> console.commandError();
        }
        command.execute();
    }

    private VoucherData inputVoucher() {
        String[] split = console.inputVoucher().split(",");
        String voucherNumber = split[0].trim();
        Long rate = Long.parseLong(split[1].trim());
        return new VoucherData(voucherNumber, UUID.randomUUID(), rate);
    }

    private Command newCreateCommand(VoucherData voucherData) {
        switch (VoucherType.findByNumber(voucherData.voucherNumber())) {
            case FIX -> command = createFixedVoucher(voucherData);
            case PERCENT -> command = createPercentVoucher(voucherData);
        }
        console.successCreate();
        return command;
    }

    private CreateCommand createFixedVoucher(VoucherData voucherData) {
        return new CreateCommand(new FixedAmountVoucher(UUID.randomUUID(), voucherData.rate()), voucherService);
    }

    private CreateCommand createPercentVoucher(VoucherData voucherData) {
        return new CreateCommand(new PercentDiscountVoucher(UUID.randomUUID(), voucherData.rate()), voucherService);
    }

}
