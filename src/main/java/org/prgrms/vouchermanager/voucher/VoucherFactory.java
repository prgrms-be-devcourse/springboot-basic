package org.prgrms.vouchermanager.voucher;

import java.util.UUID;

/**
 * VoucherType에 따라 voucher객체를 반환하는 팩토리
 */
public class VoucherFactory {

    public static Voucher getVoucher(VoucherType type, long amount) {
        //TODO : 타입을 찾지 못했을 경우 null 반환하지 않도록 처리
        Voucher voucher = null;
        if (type.equals(VoucherType.FIXED)) voucher = new FixedAmountVoucher(UUID.randomUUID(), amount);
        if (type.equals(VoucherType.PERCENT)) voucher = new PercentDiscountVoucher(UUID.randomUUID(), amount);
        return voucher;
    }

}
