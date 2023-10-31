package org.prgrms.prgrmsspring.entity.voucher;

import org.prgrms.prgrmsspring.domain.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;


public class PercentDiscountVoucher extends Voucher {

    public PercentDiscountVoucher(UUID voucherId, long amount) {
        super(voucherId, amount, VoucherType.PERCENT_DISCOUNT.getTitle());
        if (amount < 0 || amount > 100) throw new IllegalArgumentException("비율 할인 금액은 0과 100 사이의 금액이어야 합니다.");
    }

    public PercentDiscountVoucher(UUID voucherId, long amount, LocalDateTime dateTime) {
        super(voucherId, amount, VoucherType.PERCENT_DISCOUNT.getTitle(), dateTime);
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (amount / 100);
    }
}
