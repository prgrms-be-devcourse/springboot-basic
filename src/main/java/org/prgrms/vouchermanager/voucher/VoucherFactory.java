package org.prgrms.vouchermanager.voucher;

/**
 * VoucherType에 따라 voucher객체를 반환하는 팩토리
 */
public class VoucherFactory {

    public static Voucher getVoucher(VoucherType type, long amount) {
        Voucher voucher = null;
        switch (type){
            case FIXED -> voucher = new FixedAmountVoucher(amount);
            case PERCENT -> voucher = new PercentDiscountVoucher(amount);
        }
        return voucher;
    }

}
