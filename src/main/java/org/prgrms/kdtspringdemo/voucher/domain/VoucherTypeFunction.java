package org.prgrms.kdtspringdemo.voucher.domain;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.UUID;

public enum VoucherTypeFunction {
    FIXED_AMOUNT_VOUCHER("fixedAmount", "고정 할인 바우처") {
        @Override
        public Voucher create(UUID voucherId, long amount) {
            return new FixedAmountVoucher(voucherId, amount);
        }
    },
    PERCENT_DISCOUNT_VOUCHER("percentDiscount", "비율 할인 바우처") {
        @Override
        public Voucher create(UUID voucherId, long amount) {
            return new PercentDiscountVoucher(voucherId, amount);
        }
    };

    private final String type;
    private final String description;

    VoucherTypeFunction(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public static VoucherTypeFunction findByCode(String type) {
        return Arrays.stream(values())
                .filter(option -> option.type.equals(type))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("해당 타입의 바우처가 존재하지 않습니다."));
    }

    public abstract Voucher create(UUID voucherId, long amount);

}
