package com.prgrms.model.voucher;

import com.prgrms.io.ErrorMessage;
import com.prgrms.model.voucher.discount.Discount;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("1",
            "얼만큼 할인 받고 싶은지 입력하세요 :",
                          FixedAmountVoucher::new),
    PERCENT_DISCOUNT_VOUCHER("2",
            "할인율을 입력하세요 :",
            PercentDiscountVoucher::new);

    private final String number;
    private final String discountGuide;
    private final BiFunction<Discount,VoucherType, Voucher> voucherFunction ;

    VoucherType(String number, String discountGuide, BiFunction<Discount,VoucherType, Voucher> voucherFunction) {
        this.number = number;
        this.discountGuide = discountGuide;
        this.voucherFunction = voucherFunction;
    }

    public static VoucherType findByPolicy(String policy) {
        return Arrays.stream(VoucherType.values())
                .filter(p -> p.number.equals(policy))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException(ErrorMessage.INVALID_SELECTION.getMessage()));
    }

    public String voucherPolicyOptionGuide() {
        return number + "번 : " + name();
    }

    public String discountGuide() {
        return discountGuide;
    }

    public Voucher createVoucher(Discount amount) {
        return voucherFunction.apply(amount, this);
    }

}
