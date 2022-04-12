package org.voucherProject.voucherProject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.voucherProject.voucherProject.controller.voucher.VoucherController;
import org.voucherProject.voucherProject.entity.voucher.Voucher;
import org.voucherProject.voucherProject.entity.voucher.VoucherType;
import org.voucherProject.voucherProject.io.Console;
import java.util.List;


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

                switch (inputCommend) {
                    case EXIT:
                        console.endMessage();
                        System.exit(0);
                        break;
                    case LIST:
                        List<Voucher> vouchers = voucherController.findAll();
                        vouchers.forEach(voucher -> System.out.println("voucher = " + voucher));
                        break;
                    case CREATE:
                        String inputVoucherType = console.input("1. FixedAmountVoucher\n2. PercentDiscountVoucher");
                        VoucherType voucherType = VoucherType.getVoucherType(Integer.parseInt(inputVoucherType));
                        long inputDiscountAmount = Long.parseLong(console.input("할인 수치를 입력해주세요"));
                        voucherController.createVoucher(voucherType.createVoucher(inputDiscountAmount));
                        console.completeMessage();
                        break;
                }
            } catch (IllegalArgumentException e) {
                console.errorMessage();
            }
        }
    }
}
