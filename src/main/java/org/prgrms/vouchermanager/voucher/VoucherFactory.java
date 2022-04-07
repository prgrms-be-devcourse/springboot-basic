package org.prgrms.vouchermanager.voucher;

import java.util.UUID;

public class VoucherFactory {

    public static Voucher getVoucher(VoucherType type, long amount) {
        Voucher voucher = null;
        if (type.equals(VoucherType.FIXED)) voucher = new FixedAmountVoucher(UUID.randomUUID(), amount);
        if (type.equals(VoucherType.PERCENT)) voucher = new PercentDiscountVoucher(UUID.randomUUID(), amount); // amount 변수 이름..
        return voucher;
    }

}
