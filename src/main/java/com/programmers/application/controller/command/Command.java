package com.programmers.application.controller.command;

import com.programmers.application.controller.command.execution.VoucherExecution;
import com.programmers.application.dto.reponse.VoucherInfoResponse;
import com.programmers.application.dto.request.RequestFactory;
import com.programmers.application.dto.request.VoucherCreationRequest;
import com.programmers.application.io.IO;
import com.programmers.application.service.VoucherService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public enum Command {
    EXIT, CREATE, LIST;

    public void executeVoucher(VoucherService voucherService, Command command, IO io) throws IOException {
        switch (command) {
            case EXIT -> io.write("Voucher 서비스를 종료합니다.");
            case CREATE -> {
                VoucherExecution voucherExecution = new VoucherExecution() {
                    private static final String SEPARATOR = " ";
                    private static final int TYPE_INDEX = 0;
                    private static final int AMOUNT_INDEX = 1;

                    @Override
                    public void execute() throws IOException {
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
                        if (Objects.isNull(type) || type.isBlank()) {
                            String errorMessage = String.format("옳바른 타입을 입력해 주세요. 입력값: %s", type);
                            throw new IllegalArgumentException(errorMessage);
                        }
                    }

                    private void validateAmount(String amount) {
                        if (!amount.matches("[0-9]+")) {
                            String errorMessage = String.format("숫자를 입력해 주세요. 입력값: %s", amount);
                            throw new IllegalArgumentException(errorMessage);
                        }
                    }

                    private void printOption() throws IOException {
                        io.write("=== Create Voucher ===");
                        io.write("Enter a fixed or percent to create the discount voucher");
                        io.write("Enter the amount of the discount voucher");
                        io.write("For example) fixed 1000");
                    }
                };
                voucherExecution.execute();
            }
            case LIST -> {
                VoucherExecution voucherExecution = () -> {
                    List<VoucherInfoResponse> voucherList = voucherService.findVoucherList();
                    for (VoucherInfoResponse voucherInfoResponse : voucherList) {
                        io.write(voucherInfoResponse.toString());
                    }
                };
                voucherExecution.execute();
            }
        }
    }
}
