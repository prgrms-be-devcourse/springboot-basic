package org.prgrms.kdt.voucher.model;

import java.util.Arrays;
import java.util.function.Function;
import org.prgrms.kdt.error.ErrorMessage;

public enum VoucherType {
    FIXED("fixed",  FixedAmountVoucher::create),
    PERCENT("percent", PercentDiscountVoucher::create),;

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

}
