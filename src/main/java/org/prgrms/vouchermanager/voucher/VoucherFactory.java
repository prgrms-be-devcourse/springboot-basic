package org.prgrms.vouchermanager.voucher;

import java.util.UUID;

/**
 * VoucherType에 따라 voucher객체를 반환하는 팩토리
 */
public class VoucherFactory {

    public static Voucher getVoucher(VoucherType type, long amount) {
        Voucher voucher = null;
        switch (type){
            case FIXED -> voucher = new FixedAmountVoucher(UUID.randomUUID(), amount);
            case PERCENT -> voucher = new PercentDiscountVoucher(UUID.randomUUID(), amount);
        }

        return voucher;
    }

}
