package org.prgrms.deukyun.voucherapp.view.command;

import lombok.RequiredArgsConstructor;
import org.prgrms.deukyun.voucherapp.view.console.ConsoleService;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.Voucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.VoucherFactory;
import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 * 바우처 생성 커맨드
 */
@ShellComponent
@RequiredArgsConstructor
public class CreateVoucherCommand {

    private final VoucherService voucherService;
    private final ConsoleService console;

    @ShellMethod(value = "create a voucher", key = "create")
    public void createVoucher() {
        console.write("enter the type of voucher (fixed/percent)");
        String type = console.readLine();

        console.write("enter the amount/percent");
        long argument = console.readLong();

        Voucher voucher = VoucherFactory.createVoucher(type, argument);
        voucherService.insert(voucher);
    }
}
