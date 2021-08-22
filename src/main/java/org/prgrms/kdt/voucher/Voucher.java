package org.prgrms.kdt.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long discount(long beforeDiscount);

    /**
     * voucherType에 맞는 Voucher 객체를 생성하는 팩토리 메소드.
     */
    static Voucher voucherFactory(VoucherType voucherType, long size){
        Voucher newVoucher = null;

        switch(voucherType){
            case FIXED_AMOUNT:
                newVoucher = new FixedAmountVoucher(UUID.randomUUID(), size);
                break;
            case PERSENT_DISCOUNT:
                newVoucher = new PercentDiscountVoucher(UUID.randomUUID(), size);
                break;
            default:
                return null;
        }

        return newVoucher;
    }

    static Voucher voucherFactory(VoucherType voucherType, long size, UUID voucherId){
        Voucher newVoucher = null;

        switch(voucherType){
            case FIXED_AMOUNT:
                newVoucher = new FixedAmountVoucher(voucherId, size);
                break;
            case PERSENT_DISCOUNT:
                newVoucher = new PercentDiscountVoucher(voucherId, size);
                break;
            default:
                return null;
        }

        return newVoucher;
    }
}
