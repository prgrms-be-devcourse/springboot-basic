package org.prgrms.kdt.domain.voucher;

import org.prgrms.kdt.enums.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {

    UUID getCustomerId();

    UUID getVoucherId();

    long getDiscount();

    long discount(long beforeDiscount);

    VoucherType getVoucherType();

    String toString();

}