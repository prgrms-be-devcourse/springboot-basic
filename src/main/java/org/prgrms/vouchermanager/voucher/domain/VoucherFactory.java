package org.prgrms.vouchermanager.voucher.domain;

/**
 * VoucherType에 따라 voucher객체를 반환하는 팩토리
 */
public class VoucherFactory {

    public static Voucher getVoucher(VoucherType type, long amount) {
        switch (type){
            case FIXED -> {return new FixedAmountVoucher(amount);}
            case PERCENT -> {return new PercentDiscountVoucher(amount);}
        }
        throw new IllegalArgumentException("잘못된 type 값 argument");
    }

}
