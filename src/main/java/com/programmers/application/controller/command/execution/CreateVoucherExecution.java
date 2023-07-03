package com.programmers.application.controller.command.execution;

import com.programmers.application.dto.request.RequestFactory;
import com.programmers.application.dto.request.VoucherCreationRequest;
import com.programmers.application.io.IO;
import com.programmers.application.service.VoucherService;

import java.io.IOException;
import java.util.Objects;

public class CreateVoucherExecution implements VoucherExecution{
    private static final String SEPARATOR = " ";
    private static final int TYPE_INDEX = 0;
    private static final int AMOUNT_INDEX = 1;
    private final VoucherService voucherService;
    private final IO io;

    public CreateVoucherExecution(VoucherService voucherService, IO io) {
        this.voucherService = voucherService;
        this.io = io;
    }

    @Override
    public void run() throws IOException {
        printOption();
        String[] typeAndAmount = io.read().split(SEPARATOR);
        String type = typeAndAmount[TYPE_INDEX];
        String amount = typeAndAmount[AMOUNT_INDEX];
        validateType(type);
        validateAmount(amount);
        VoucherCreationRequest voucherCreationRequest = RequestFactory.createVoucherCreationRequest(type, Long.parseLong(amount));
        voucherService.createVoucher(voucherCreationRequest);
    }

    private static void validateType(String type) {
        if (Objects.isNull(type) || type.isBlank() || type.isEmpty()) {
            throw new IllegalArgumentException("옳바른 타입을 입력해 주세요. 입력값: " + type);
        }
    }

    private void validateAmount(String amount) {
        if (!amount.matches("[0-9]+")) {
            throw new IllegalArgumentException("숫자를 입력해 주세요. 입력값: " + amount);
        }
    }

    private void printOption() throws IOException {
        io.write("=== Create Voucher ===");
        io.write("Enter a fixed or percent to create the discount voucher");
        io.write("Enter the amount of the discount voucher");
        io.write("For example) fixed 1000");
    }
}
