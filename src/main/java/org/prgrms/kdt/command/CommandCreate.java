package org.prgrms.kdt.command;

import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;

import java.util.Optional;
import java.util.Scanner;

public class CommandCreate {

    public static Optional<Voucher> createVoucherType() {
        final Scanner scanner = new Scanner(System.in);
        String voucherType;

        while (true) {
            voucherType = scanner.nextLine();

            switch (voucherType) {
                case "FixedAmountVoucher", "PercentDiscountVoucher" -> {
                    NavigationMessage.howMuchDiscountMessage(voucherType);
                    return Optional.ofNullable(VoucherService.createVoucher( // voucher 생성
                            voucherType, ValueValidation.inputValueValidation(voucherType) // 유효한 값인지 생성 및 검사
                    ));
                }

                default -> NavigationMessage.howMuchDiscountMessage(voucherType);
            }
        }
    }
}
