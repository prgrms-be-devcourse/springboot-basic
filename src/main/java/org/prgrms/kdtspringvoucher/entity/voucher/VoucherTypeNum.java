package org.prgrms.kdtspringvoucher.entity.voucher;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public enum VoucherTypeNum {
    FIXED("fixed") {
        public Voucher create(UUID voucherId, long amount) {
            return new FixedAmountVoucher(voucherId, amount, LocalDateTime.now());
        }
    },
    PERCENT("percent") {
        public Voucher create(UUID voucherId, long amount) {
            return new PercentDiscountVoucher(voucherId, amount, LocalDateTime.now());
        }
    };

    private final String type;

    VoucherTypeNum(String type) {
        this.type = type;
    }

    public static Voucher getVoucherType(String inputType, UUID voucherId, long value) {
        VoucherTypeNum voucherType = Arrays.stream(VoucherTypeNum.values())
                .filter(voucher -> voucher.type.equalsIgnoreCase(inputType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 바우처 타입을 입력하였습니다."));

        return voucherType.create(voucherId, value);
    }

    public static int getVoucherTypeNum(String inputType) {
        VoucherTypeNum voucherType = Arrays.stream(VoucherTypeNum.values())
                .filter(voucher -> voucher.type.equalsIgnoreCase(inputType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 바우처 타입을 입력하였습니다."));

        return voucherType.ordinal();
    }


    public abstract Voucher create(UUID voucherId, long value);

}
