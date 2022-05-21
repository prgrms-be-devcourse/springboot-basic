package org.prgrms.kdt.voucher.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;
import org.prgrms.kdt.error.ErrorMessage;

public enum VoucherType {
    FIXED("fixed", FixedAmountVoucher::new){
        @Override
        public Voucher convert(UUID id, long value, LocalDateTime createdAt) {
            return new FixedAmountVoucher(id, value, createdAt);
        }
    },
    PERCENT("percent", PercentDiscountVoucher::new){
        @Override
        public Voucher convert(UUID id, long value, LocalDateTime createdAt) {
            return new PercentDiscountVoucher(id, value, createdAt);
        }
    };

    private final String keyword;
    private final Function<Long, Voucher> factory;

    VoucherType(String keyword, Function<Long, Voucher> factory) {
        this.keyword = keyword;
        this.factory = factory;
    }

    public static VoucherType of(String keyword) {
        return Arrays.stream(VoucherType.values())
            .filter(v -> v.getKeyword().equals(keyword))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NOTFOUND_VOUCHER.getMessage()));
    }

    public String getKeyword() {
        return keyword;
    }

    public Voucher create(long value) {
        return factory.apply(value);
    }

    public abstract Voucher convert(UUID id, long value, LocalDateTime createdAt);

}
