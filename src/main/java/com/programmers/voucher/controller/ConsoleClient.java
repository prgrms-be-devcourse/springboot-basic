package com.programmers.voucher.controller;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.enumtype.ConsoleCommandType;
import com.programmers.voucher.enumtype.VoucherType;
import com.programmers.voucher.io.Console;
import com.programmers.voucher.request.VoucherCreateRequest;
import com.programmers.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ConsoleClient implements VoucherController {
    private final Console console;
    private final VoucherService voucherService;

    public ConsoleClient(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    public void start() {
        ConsoleCommandType commandType = console.init();

        switch (commandType) {
            case CREATE -> {
                String rawVoucherType = console.input("1. [fixed | percent]");
                VoucherType voucherType = VoucherType.getValue(rawVoucherType);

                Integer amount = console.intInput("2. [amount]");

                VoucherCreateRequest voucherCreateRequest = new VoucherCreateRequest(voucherType, amount);
                UUID voucherId = createVoucher(voucherCreateRequest);

                console.print("Created new voucher. VoucherID: " + voucherId.toString());
            }
        }
    }

    @Override
    public UUID createVoucher(VoucherCreateRequest request) {
        return voucherService.createVoucher(request);
    }

    @Override
    public List<Voucher> findVouchers() {
        return null;
    }
}
