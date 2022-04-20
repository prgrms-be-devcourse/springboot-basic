package org.prgrms.deukyun.voucherapp.app.command;

import org.prgrms.deukyun.voucherapp.app.console.ConsoleService;
import org.prgrms.deukyun.voucherapp.domain.voucher.entity.FixedAmountDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.entity.PercentDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.entity.Voucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.text.MessageFormat;

/**
 * 바우처 생성 커맨드
 */
@ShellComponent
public class CreateVoucherCommand {

    private final VoucherService voucherService;
    private final ConsoleService console;

    public CreateVoucherCommand(VoucherService voucherService, ConsoleService console) {
        this.voucherService = voucherService;
        this.console = console;
    }

    @ShellMethod(value = "create a voucher", key = "create")
    public void createVoucher() {
        console.write("enter the type of voucher (fixed/percent)");
        String type = console.readLine();

        if (isFADVRequest(type)) {
            console.write("enter the amount");
            long amount = readLong();

            insert(new FixedAmountDiscountVoucher(amount));
        } else if (isPDVRequest(type)) {
            console.write("enter the percent");
            long percent = readLong();

            insert(new PercentDiscountVoucher(percent));
        } else {
            throw new IllegalArgumentException(MessageFormat.format("No Such type {0}", type));
        }
    }

    private boolean isFADVRequest(String type) {
        return type.equals("fixed");
    }

    private boolean isPDVRequest(String type) {
        return type.equals("percent");
    }

    private long readLong() {
        try {
            return Long.parseLong(console.readLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("You should Enter Number");
        }
    }

    private void insert(Voucher voucher) {
        voucherService.insert(voucher);
    }
}
