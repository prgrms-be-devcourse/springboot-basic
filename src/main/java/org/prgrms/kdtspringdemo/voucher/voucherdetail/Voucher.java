package org.prgrms.kdtspringdemo.voucher.voucherdetail;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    public long discount(long beforeDiscount);

    long getAmount();
}
