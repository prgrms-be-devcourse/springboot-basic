package org.prgrms.kdt.voucher.model;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long discount(long beforeDiscount);

    long getBenefit();
    int getVoucherTypeNum();
    String getVoucherName();
}
