package org.prgrms.kdt.shop.domain;

import org.prgrms.kdt.shop.enums.OrderStatus;
import org.prgrms.kdt.shop.enums.VoucherType;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;
    private final VoucherType type = VoucherType.PERCENT_DISCOUNT;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId( ) {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - (int) beforeDiscount / percent;
    }

    @Override
    public long getAmount( ) {
        return percent;
    }

    @Override
    public VoucherType getVoucherType( ) {
        return type;
    }

}
