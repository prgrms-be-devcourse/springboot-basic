package com.programmers.application.domain.command.execution;

import com.programmers.application.dto.request.RequestFactory;
import com.programmers.application.dto.request.VoucherCreationRequest;
import com.programmers.application.io.IO;
import com.programmers.application.service.VoucherService;

import java.util.Arrays;

public class CreateVoucherExecution implements VoucherExecution{
    private final VoucherService voucherService;
    private final IO io;

    public CreateVoucherExecution(VoucherService voucherService, IO io) {
        this.voucherService = voucherService;
        this.io = io;
    }

    @Override
    public void run() {
        printOption(io);
        String[] typeAndAmount = io.read().split(" ");
        validateAmount(typeAndAmount);
        VoucherCreationRequest voucherCreationRequest = RequestFactory.createVoucherCreationRequest(typeAndAmount[0], Long.parseLong(typeAndAmount[1]));
        voucherService.createVoucher(voucherCreationRequest);
    }

    private static void validateAmount(String[] typeAndAmount) {
        if (!typeAndAmount[1].matches("[0-9]+")) {
            throw new IllegalArgumentException("숫자를 입력해 주세요. 입력값: " + Arrays.toString(typeAndAmount));
        }
    }

    private static void printOption(IO io) {
        io.write("=== Create Voucher ===");
        io.write("Enter a fixed or percent to create the discount voucher");
        io.write("Enter the amount of the discount voucher");
        io.write("For example) enter fixed 1000 like this");
    }
}
