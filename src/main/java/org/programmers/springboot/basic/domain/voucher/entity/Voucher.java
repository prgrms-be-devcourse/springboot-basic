package org.programmers.springboot.basic.domain.voucher.entity;

import java.io.Serializable;
import java.util.UUID;

public interface Voucher extends Serializable {

    UUID getVoucherId();
    VoucherType getVoucherType();
    Long getDiscount();
}
