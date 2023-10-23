package com.programmers.vouchermanagement.controller;

import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import com.programmers.vouchermanagement.dto.voucher.CreateVoucherRequestDto;
import com.programmers.vouchermanagement.infra.io.ConsoleInput;
import com.programmers.vouchermanagement.infra.io.ConsoleOutput;
import com.programmers.vouchermanagement.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class VoucherShellController {
    private final VoucherService voucherService;

    @ShellMethod(key = "create")
    public void create() {
        ConsoleInput consoleInput = new ConsoleInput();

        CreateVoucherRequestDto request = new CreateVoucherRequestDto();
        request.setVoucherType(VoucherType.select(consoleInput.readVoucherTypeId()));
        request.setAmount(consoleInput.readAmount());

        voucherService.createVoucher(request);
    }

    @ShellMethod(key = "list")
    public void list() {
        List<Voucher> vouchers = voucherService.getVouchers();
        for (Voucher voucher : vouchers) {
            ConsoleOutput.println(voucher.toString());
        }
    }
}
