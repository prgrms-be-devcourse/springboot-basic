package com.programmers.commandline.domain.voucher.entity;

import com.programmers.commandline.domain.voucher.entity.impl.FixedAmountVoucher;
import com.programmers.commandline.domain.voucher.entity.impl.PercentDiscountVoucher;
import com.programmers.commandline.global.io.Message;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public enum VoucherType {
    FIXED_AMOUNT(1, "할인금액 : ", ((id, discount, createdAt) -> FixedAmountVoucher.of(id, discount))),
    PERCENT_DISCOUNT(2, "할인율 : ", ((id, discount, createdAt) -> PercentDiscountVoucher.of(id, discount))),
    ;

    private final int code;
    private final String message;
    private final LambdaCreateVoucher lambdaCreateVoucher;


    VoucherType(int code, String message, LambdaCreateVoucher lambdaCreateVoucher) {
        this.code = code;
        this.message = message;
        this.lambdaCreateVoucher = lambdaCreateVoucher;
    }

    public static VoucherType ofNumber(int code) {

        return Arrays.stream(VoucherType.values())
                .filter(voucherMenu -> voucherMenu.code == code)
                .findFirst()
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(Message.VOUCHER_MENU_ERROR.getMessage());
                });
    }

    public Voucher createVoucher(UUID uuid, long discount, LocalDateTime createdAt) {
        return lambdaCreateVoucher.create(uuid, discount, createdAt);
    }

    public String getMessage() {
        return this.message;
    }
}
