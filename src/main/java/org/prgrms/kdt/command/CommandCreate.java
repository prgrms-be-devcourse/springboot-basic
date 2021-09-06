package org.prgrms.kdt.command;

import org.prgrms.kdt.voucher.Validation;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.repository.MemoryVoucherRepository;

import java.util.Optional;
import java.util.Scanner;

public class CommandCreate {
    public static void voucherCreateMessage() {
        System.out.println("=== Voucher Create ===");
        System.out.println("Choose the type of voucher.");
        System.out.println("- FixedAmountVoucher");
        System.out.println("- PercentDiscountVoucher");
    }

    public static Optional<Voucher> createVoucherType() {
        final Scanner scanner = new Scanner(System.in);
        String voucherType;

        while (true) {
            voucherType = scanner.nextLine();

            switch (voucherType) {
                case "FixedAmountVoucher", "PercentDiscountVoucher" -> {
                    new HowMuchDiscountMessage(voucherType);
                    return Optional.ofNullable(MemoryVoucherRepository.create( // voucher 생성
                            voucherType, Validation.inputValueValidation(voucherType) // 유효한 값인지 생성 및 검사
                    ));
                }

                default -> new HowMuchDiscountMessage(voucherType);
            }
        }
    }
}
