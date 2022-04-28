package org.prgrms.deukyun.voucherapp.view.command;

import lombok.RequiredArgsConstructor;
import org.prgrms.deukyun.voucherapp.view.console.ConsoleService;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.Voucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.stream.Collectors;

/**
 * 바우처 전체 출력 커맨드
 */
@ShellComponent
@RequiredArgsConstructor
public class ShowAllVoucherCommand {

    private final VoucherService voucherService;
    private final ConsoleService console;

    @ShellMethod(value = "show all voucher", key = "list")
    public void showAllVoucher() {

        console.write(
            voucherService.findAll().stream()
                .map(Voucher::toDisplayString)
                .collect(Collectors.joining("\n"))
        );
    }
}
