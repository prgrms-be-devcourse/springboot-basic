package org.prgrms.kdt.domain.voucher;

import org.prgrms.kdt.utils.VoucherType;

public interface Voucher {

    Long getVoucherId();

    long discount(long beforeDiscount);

    VoucherType getVoucherType();

    Long getDiscountAmount();

    boolean getStatus();
}
