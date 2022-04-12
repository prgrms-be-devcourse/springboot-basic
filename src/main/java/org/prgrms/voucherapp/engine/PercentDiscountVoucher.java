package org.prgrms.voucherapp.engine;

import org.prgrms.voucherapp.exception.WrongAmountException;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    // TODO : MAX_VOUCHER_AMOUNT YAML 파일로 외부 설정으로 주입하기
//    private static final long MAX_VOUCHER_AMOUNT = 100;
    private final UUID voucherId;
    private final long percentAmount;

    public PercentDiscountVoucher(UUID voucherId, long percentAmount) {
        this.percentAmount = percentAmount;
        this.voucherId = voucherId;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        long discountedPrice = (long) (beforeDiscount * (percentAmount / 100.0));
        return (discountedPrice < 0) ? 0 : discountedPrice;
    }
}
