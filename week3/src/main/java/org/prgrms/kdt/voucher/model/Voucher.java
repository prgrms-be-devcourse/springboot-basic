package org.prgrms.kdt.voucher.model;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long getAmount();

    long discount(long beforeDiscount);

    UUID getWalletId();

    void setWalletId(UUID walletId);

    VoucherType getVoucherType();
}
