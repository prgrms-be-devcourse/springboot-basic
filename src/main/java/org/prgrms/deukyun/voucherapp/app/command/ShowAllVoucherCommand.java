package org.prgrms.deukyun.voucherapp.app.command;

import org.prgrms.deukyun.voucherapp.app.console.ConsoleService;
import org.prgrms.deukyun.voucherapp.domain.voucher.entity.Voucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.stream.Collectors;

/**
 * 바우처 전체 출력 커맨드
 */
@ShellComponent
public class ShowAllVoucherCommand {

    private final VoucherService voucherService;
    private final ConsoleService console;

    public ShowAllVoucherCommand(VoucherService voucherService, ConsoleService console) {
        this.voucherService = voucherService;
        this.console = console;
    }

    @ShellMethod(value = "show all voucher", key = "list")
    public void showAllVoucher() {

        console.write(
                voucherService.findAll().stream()
                        .map(Voucher::toDisplayString)
                        .collect(Collectors.joining("\n"))
        );
    }
}
