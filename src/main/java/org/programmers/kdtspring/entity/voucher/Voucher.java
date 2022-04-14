package org.programmers.kdtspring.entity.voucher;

import java.util.UUID;

public abstract class Voucher {

    public abstract UUID getVoucherId();

    public abstract long discount(long beforeDiscount);

    public abstract long getDiscount();

    public abstract VoucherType getVoucherType();

}
