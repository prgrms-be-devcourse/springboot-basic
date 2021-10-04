package org.prgrms.dev.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {
    UUID getVoucherId(); // 바우처 아이디를 가져오는 행위

    long getDiscountValue();

    VoucherType getVoucherType();

    LocalDateTime getCreatedAt();

    long discount(long beforeDiscount);
}
