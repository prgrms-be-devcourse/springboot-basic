package org.prgrms.kdt.command;

import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;

public class CommandCreate {

    public static Voucher createVoucherType() {
        String voucherType;

        while (true) {
            voucherType = Input.input();

            Output.howMuchDiscount(voucherType);
            switch (voucherType) {
                case "FixedAmountVoucher", "PercentDiscountVoucher" -> {
                    return VoucherService.createVoucher( // voucher 생성
                            voucherType, ValueValidation.inputValueValidation(voucherType) // 유효한 값인지 생성 및 검사
                    );
                }
            }
        }
    }
}
