package com.programmers.vouchermanagement.controller;

import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import com.programmers.vouchermanagement.dto.voucher.request.CreateVoucherRequestDto;
import com.programmers.vouchermanagement.dto.voucher.response.VoucherResponseDto;
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

        VoucherType type = VoucherType.select(consoleInput.readVoucherTypeId());
        Long amount = consoleInput.readAmount();
        CreateVoucherRequestDto request = new CreateVoucherRequestDto(type, amount);

        voucherService.createVoucher(request);
    }

    @ShellMethod(key = "list")
    public void list() {
        List<VoucherResponseDto> vouchers = voucherService.getVouchers();
        for (VoucherResponseDto voucher : vouchers) {
            ConsoleOutput.println(voucher.toString());
        }
    }
}
