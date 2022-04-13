package org.voucherProject.voucherProject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.voucherProject.voucherProject.controller.voucher.VoucherController;
import org.voucherProject.voucherProject.entity.voucher.Voucher;
import org.voucherProject.voucherProject.entity.voucher.VoucherDto;
import org.voucherProject.voucherProject.entity.voucher.VoucherType;
import org.voucherProject.voucherProject.io.Console;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@Component
public class VoucherEnrollSystem implements Runnable {

    private final Console console;
    private final VoucherController voucherController;

    @Override
    public void run() {
        while (true) {
            String inputString = console.input(
                    "Type exit to exit the program.\n" +
                    "Type create to create a new voucher.\n" +
                    "Type list to list all vouchers");
            try {
                InputCommend inputCommend = InputCommend.is(inputString);
                executeSystem(inputCommend);
            } catch (IllegalArgumentException e) {
                console.errorMessage();
            }
        }
    }

    private void executeSystem(InputCommend inputCommend) {
        switch (inputCommend) {
            case EXIT:
                console.endMessage();
                System.exit(0);
                break;
            case LIST:
                voucherController.printAll();
                break;
            case CREATE:
                VoucherDto voucherDto = new VoucherDto();
                String inputVoucherType = console.input("1. FixedAmountVoucher\n2. PercentDiscountVoucher");

                VoucherType voucherType = VoucherType.getVoucherType(Integer.parseInt(inputVoucherType));
                voucherDto.setVoucherType(voucherType);
                long amount = Long.parseLong(console.input("할인 수치를 입력해주세요"));
                voucherDto.setAmount(amount);
                voucherController.createVoucher(voucherDto);
                console.completeMessage();
                break;
        }
    }
}
