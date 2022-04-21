package org.prgrms.kdt.shop.domain;

import org.prgrms.kdt.shop.enums.VoucherType;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId( );

    long discount(long beforeDiscount);

    long getAmount( );

    VoucherType getVoucherType( );
}
