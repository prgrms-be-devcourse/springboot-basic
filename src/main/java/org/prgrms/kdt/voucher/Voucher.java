package org.prgrms.kdt.voucher;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long getVoucherDiscount();

    String getVoucherType();

    long discount(long beforeDiscount);
}
