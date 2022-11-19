package com.programmers.commandline.domain.voucher.entity;

import com.programmers.commandline.domain.voucher.entity.impl.FixedAmountVoucher;
import com.programmers.commandline.domain.voucher.entity.impl.PercentDiscountVoucher;
import com.programmers.commandline.global.io.Message;
import com.programmers.commandline.global.util.Verification;

import java.util.Arrays;
import java.util.UUID;

public enum VoucherType {
    FIXED_AMOUNT(1, "할인금액 : ", ((id, discount) -> new FixedAmountVoucher(id, discount))),
    PERCENT_DISCOUNT(2, "할인율 : ", ((id, discount) -> new PercentDiscountVoucher(id, discount))),;

    private final int code;
    private final String message;
    private final LambdaVoucher lambdaVoucher;


    VoucherType(int code, String message, LambdaVoucher lambdaVoucher) {
        this.code = code;
        this.message = message;
        this.lambdaVoucher = lambdaVoucher;
    }

    public Voucher createVoucher(UUID uuid, long discount) {
        return lambdaVoucher.create(uuid, discount);
    }

    public static VoucherType ofNumber(String input) {
        int code = toCode(input);

        return Arrays.stream(VoucherType.values())
                .filter(voucherMenu -> voucherMenu.code == code)
                .findFirst()
                .orElseThrow(() -> {
                    throw new RuntimeException(Message.VOUCHER_MENU_ERROR.getMessage());
                });
    }

    private static int toCode(String input) {
        Verification.validateParseToNumber(input);
        return Integer.parseInt(input);
    }

    public String getMessage() {
        return this.message;
    }
}
