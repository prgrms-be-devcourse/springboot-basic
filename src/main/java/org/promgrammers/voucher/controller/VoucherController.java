package org.promgrammers.voucher.controller;

import lombok.RequiredArgsConstructor;
import org.promgrammers.voucher.domain.dto.voucher.VoucherListResponseDto;
import org.promgrammers.voucher.domain.dto.voucher.VoucherCreateRequestDto;
import org.promgrammers.voucher.domain.dto.voucher.VoucherResponseDto;
import org.promgrammers.voucher.service.VoucherService;
import org.promgrammers.voucher.view.Console;
import org.promgrammers.voucher.view.Option;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;
    private final Console console;

    private boolean isRunning = true;

    public synchronized void userPostController() {
        while (isRunning) {
            try {
                console.display();
                String input = console.input();
                Option option = Option.fromOption(input);
                execute(option);
            } catch (RuntimeException e) {
                console.print(e.getMessage());
            }
        }
    }

    private void execute(Option option) {
        switch (option) {
            case CREATE:
                createVoucher();
                break;
            case LIST:
                listVouchers();
                break;
            case EXIT:
                exitProgram();
                break;
        }
    }

    private void createVoucher() {
        VoucherCreateRequestDto voucherDto = console.createVoucherDto();
        VoucherResponseDto createdVoucher = voucherService.createVoucher(voucherDto);
        console.print("Voucher 생성. : " + createdVoucher.toString());
    }

    private void listVouchers() {
        VoucherListResponseDto voucherList = voucherService.findAll();
        console.print(voucherList.toString());
    }

    private void exitProgram() {
        isRunning = false;
    }
}