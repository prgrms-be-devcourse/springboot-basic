package org.prgrms.kdt.domain.voucher;

import org.prgrms.kdt.utils.VoucherType;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long discount(long beforeDiscount);

    VoucherType getVoucherType();

    Long getDiscountAmount();
}
