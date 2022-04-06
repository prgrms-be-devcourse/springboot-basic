package org.prgrms.vouchermanager.voucher;

import java.util.UUID;

public class VoucherFactory {

    public static Voucher getVoucher(String type, long amount) {
        Voucher voucher = null;
        if (type.equals("fixed")) voucher = new FixedAmountVoucher(UUID.randomUUID(), amount);
        if (type.equals("percent")) voucher = new PercentDiscountVoucher(UUID.randomUUID(), amount); // amount 변수 이름..
        return voucher;
    }

}
