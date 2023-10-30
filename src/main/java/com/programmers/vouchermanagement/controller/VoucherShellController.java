package com.programmers.vouchermanagement.controller;

import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import com.programmers.vouchermanagement.dto.voucher.request.CreateVoucherRequestDto;
import com.programmers.vouchermanagement.dto.voucher.request.UpdateVoucherRequestDto;
import com.programmers.vouchermanagement.dto.voucher.response.VoucherResponseDto;
import com.programmers.vouchermanagement.infra.io.ConsoleInput;
import com.programmers.vouchermanagement.infra.io.ConsoleOutput;
import com.programmers.vouchermanagement.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.UUID;

@ShellComponent
@RequiredArgsConstructor
public class VoucherShellController {
    private final VoucherService voucherService;

    @ShellMethod(key = "create")
    public void create() {
        VoucherType.printAllDescriptionsToConsole();

        ConsoleInput consoleInput = new ConsoleInput();

        VoucherType type = VoucherType.select(consoleInput.readInt("👉Please enter a voucher type > ", "Invalid voucher type"));
        long amount = consoleInput.readLong("👉Please enter a amount > ", "Invalid amount");

        CreateVoucherRequestDto request = new CreateVoucherRequestDto(type, amount);
        voucherService.createVoucher(request);

        ConsoleOutput.println("✅Voucher created successfully");
    }

    @ShellMethod(key = "list")
    public void list() {
        List<VoucherResponseDto> vouchers = voucherService.getVouchers();
        for (VoucherResponseDto voucher : vouchers) {
            ConsoleOutput.println(voucher.toString());
        }
    }

    @ShellMethod(key = "detail")
    public void detail() {
        ConsoleInput consoleInput = new ConsoleInput();

        UUID id = consoleInput.readUUID("👉Please enter a voucher id > ", "Invalid voucher id");
        VoucherResponseDto voucher = voucherService.getVoucher(id);

        ConsoleOutput.println(voucher.toString());
    }

    @ShellMethod(key = "update")
    public void update() {
        ConsoleInput consoleInput = new ConsoleInput();

        UUID id = consoleInput.readUUID("👉Please enter a voucher id > ", "Invalid voucher id");
        long amount = consoleInput.readLong("👉Please enter a amount > ", "Invalid amount");

        UpdateVoucherRequestDto request = new UpdateVoucherRequestDto(id, amount);
        voucherService.updateVoucher(request);

        ConsoleOutput.println("✅Voucher updated successfully");
    }

    @ShellMethod(key = "delete")
    public void delete() {
        ConsoleInput consoleInput = new ConsoleInput();

        UUID id = consoleInput.readUUID("👉️Please enter a voucher id > ", "Invalid voucher id");
        voucherService.deleteVoucher(id);

        ConsoleOutput.println("✅Voucher deleted successfully");
    }
}
