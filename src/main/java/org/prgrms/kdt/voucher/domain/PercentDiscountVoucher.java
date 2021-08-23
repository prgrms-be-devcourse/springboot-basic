package org.prgrms.kdt.voucher.domain;

import lombok.Builder;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long discount;
    private final VoucherType voucherType = VoucherType.valueOf("PERCENT");

    @Builder
    public PercentDiscountVoucher(UUID voucherId, long discount) {
        this.voucherId = voucherId;
        this.discount = discount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long getDiscount() {
        return discount;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    // 퍼센트에 비율로 얼마를 할인할지에 대한 금액을 리턴함
    @Override
    public Long discount(long beforeDiscount) {
        return beforeDiscount * (discount / 100);
    }
}
