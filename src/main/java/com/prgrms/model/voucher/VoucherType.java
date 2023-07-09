package com.prgrms.model.voucher;

import com.prgrms.view.message.ErrorMessage;
import com.prgrms.model.voucher.dto.discount.Discount;

import java.text.MessageFormat;
import java.util.Arrays;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("1",
            "얼만큼 할인 받고 싶은지 입력하세요 :",
            FixedAmountVoucher::new),
    PERCENT_DISCOUNT_VOUCHER("2",
            "할인율을 입력하세요 :",
            PercentDiscountVoucher::new);

    private final String number;
    private final String discountGuide;
    private final TriFunction<Integer, Discount, VoucherType, Voucher> voucherFunction;

    VoucherType(String number, String discountGuide, TriFunction<Integer, Discount, VoucherType, Voucher> voucherFunction) {
        this.number = number;
        this.discountGuide = discountGuide;
        this.voucherFunction = voucherFunction;
    }

    public static VoucherType findByType(String voucherType) {
        return Arrays.stream(VoucherType.values())
                .filter(p -> p.number.equals(voucherType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_SELECTION.getMessage()));
    }

    public String voucherPolicyOptionGuide() {
        return MessageFormat.format("{0}번 : {1}", number, name());
    }

    public String discountGuide() {
        return discountGuide;
    }

    public Voucher createVoucher(int id, Discount discount) {
        return voucherFunction.apply(id, discount, this);
    }
}
