package com.prgrms.voucher_manage.domain.voucher.entity;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    Long getDiscountAmount();
    VoucherType getVoucherType();
}
