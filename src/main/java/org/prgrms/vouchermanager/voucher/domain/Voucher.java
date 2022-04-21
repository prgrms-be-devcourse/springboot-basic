package org.prgrms.vouchermanager.voucher.domain;

import java.io.Serializable;
import java.util.UUID;

public interface Voucher extends Serializable {
    UUID getVoucherId();

    VoucherType getVoucherType();

    long getDiscountValue();

    Long discount(long beforeDiscount);
}
