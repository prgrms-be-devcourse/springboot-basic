package org.prgrms.kdt.voucher;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created by yhh1056
 * Date: 2021/08/18 Time: 2:46 오전
 */
public enum VoucherType {
    FIX("1"), PERCENT("2");

    private String number;

    VoucherType(String number) {
        this.number = number;
    }

    public static Voucher convertCommandToVoucher(String number, Long discount) {
        return switch (findByNumber(number)) {
            case FIX -> new FixedAmountVoucher(UUID.randomUUID(), discount);
            case PERCENT -> new PercentDiscountVoucher(UUID.randomUUID(), discount);
        };

    }

    private static VoucherType findByNumber(String number) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.number.equals(number))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

}
