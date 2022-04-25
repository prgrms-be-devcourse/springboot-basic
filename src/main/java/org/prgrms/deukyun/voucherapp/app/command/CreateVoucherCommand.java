package org.prgrms.deukyun.voucherapp.app.command;

import org.prgrms.deukyun.voucherapp.app.console.ConsoleService;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.FixedAmountDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.PercentDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.Voucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.VoucherFactory;
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
    private final VoucherFactory voucherFactory;
    private final ConsoleService console;

    public CreateVoucherCommand(VoucherService voucherService, VoucherFactory voucherFactory, ConsoleService console) {
        this.voucherService = voucherService;
        this.voucherFactory = voucherFactory;
        this.console = console;
    }

    @ShellMethod(value = "create a voucher", key = "create")
    public void createVoucher() {
        console.write("enter the type of voucher (fixed/percent)");
        String type = console.readLine();

        console.write("enter the amount/percent");
        long argument = console.readLong();

        Voucher voucher = voucherFactory.createVoucher(type, argument);
        voucherService.insert(voucher);
    }
}
