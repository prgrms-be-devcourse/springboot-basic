package org.prgrms.kdt.voucher.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher extends Serializable {

    UUID getId();

    long discount(long beforeDiscount);

    VoucherType getVoucherType();

    long getValue();

    LocalDateTime getCreatedAt();

}
