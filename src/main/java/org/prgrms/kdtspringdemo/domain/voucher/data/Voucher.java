package org.prgrms.kdtspringdemo.domain.voucher.data;

import org.prgrms.kdtspringdemo.domain.voucher.type.VoucherType;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    UUID getCustomerId();

    String getType();

    public long discount(long beforeDiscount);

    long getAmount();

    Voucher changeTypeAndAmount(VoucherType voucherType, int amount);

}
