package org.prgms.w3d1.model.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long getVoucherValue();
    long discount(long beforeDiscount);
    UUID getVoucherWalletId();
    void setVoucherId(UUID voucherId);
    void setVoucherValue(long value);
    void setVoucherWalletId(UUID voucherWalletId);
}
