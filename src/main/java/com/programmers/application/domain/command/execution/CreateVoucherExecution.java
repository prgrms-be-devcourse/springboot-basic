package com.programmers.application.domain.command.execution;

import com.programmers.application.dto.request.RequestFactory;
import com.programmers.application.dto.request.VoucherCreationRequest;
import com.programmers.application.io.IO;
import com.programmers.application.service.VoucherService;

import java.io.IOException;
import java.util.Arrays;
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
        printOption(io);
        String[] typeAndAmount = io.read().split(SEPARATOR);
        validateType(typeAndAmount);
        validateAmount(typeAndAmount);
        VoucherCreationRequest voucherCreationRequest = RequestFactory.createVoucherCreationRequest(typeAndAmount[TYPE_INDEX], Long.parseLong(typeAndAmount[AMOUNT_INDEX]));
        voucherService.createVoucher(voucherCreationRequest);
    }

    private static void validateType(String[] typeAndAmount) {
        if (Objects.isNull(typeAndAmount[TYPE_INDEX]) || typeAndAmount[TYPE_INDEX].isBlank() || typeAndAmount[TYPE_INDEX].isEmpty()) {
            throw new IllegalArgumentException("옳바른 타입을 입력해 주세요. 입력값: " + Arrays.toString(typeAndAmount));
        }
    }

    private void validateAmount(String[] typeAndAmount) {
        if (!typeAndAmount[AMOUNT_INDEX].matches("[0-9]+")) {
            throw new IllegalArgumentException("숫자를 입력해 주세요. 입력값: " + Arrays.toString(typeAndAmount));
        }
    }

    private void printOption(IO io) throws IOException {
        io.write("=== Create Voucher ===");
        io.write("Enter a fixed or percent to create the discount voucher");
        io.write("Enter the amount of the discount voucher");
        io.write("For example) fixed 1000");
    }
}
