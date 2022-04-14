package org.prgrms.springbasic.domain.voucher;

import java.text.MessageFormat;
import java.util.UUID;

import static org.prgrms.springbasic.domain.voucher.VoucherType.PERCENT;
import static org.prgrms.springbasic.utils.enumm.ErrorMessage.AFTER_DISCOUNT_PRICE_ERR;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final VoucherType voucherType = PERCENT;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        long afterDiscount = beforeDiscount - (long) (beforeDiscount * (percent / (double) 100));

        checkDiscountPrice(beforeDiscount, afterDiscount);

        return afterDiscount;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}/{1}/{2}\n", voucherId, voucherType, percent);
    }

    private void checkDiscountPrice(long beforeDiscount, long afterDiscount) {
        if(afterDiscount < 0 || afterDiscount > beforeDiscount) { //할인된 가격이 0보다 작거나 본래 가격보다 크다면 예외 던짐
            throw new RuntimeException(AFTER_DISCOUNT_PRICE_ERR.getMessage());
        }
    }
}
