package org.prgrms.kdt.voucher;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;

/**
 * Created by yhh1056
 * Date: 2021/08/18 Time: 2:46 오전
 */
public enum VoucherType {
    FIX("1", voucherData -> new FixedAmountVoucher(UUID.randomUUID(), voucherData.getDiscount())),
    PERCENT("2", voucherData -> new PercentDiscountVoucher(UUID.randomUUID(), voucherData.getDiscount()));

    private final String number;
    private final Function<VoucherData, Voucher> voucherCreator;

    VoucherType(String number, Function<VoucherData, Voucher> voucherCreator) {
        this.number = number;
        this.voucherCreator = voucherCreator;
    }

    public Voucher create(VoucherData voucherData) {
        return voucherCreator.apply(voucherData);
    }

    public static VoucherType findByNumber(String number) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.number.equals(number))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

}
