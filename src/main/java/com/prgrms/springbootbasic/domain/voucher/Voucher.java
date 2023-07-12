package com.prgrms.springbootbasic.domain.voucher;

import com.prgrms.springbootbasic.enums.VoucherType;
import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long getDiscount();

    VoucherType getVoucherType();

    LocalDateTime getCreatedAt();
}
