package org.prgrms.kdt.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long discountAppliedPrice(long beforeDiscount);

    long getVoucherDiscountValue();

    String getVoucherName();
}
