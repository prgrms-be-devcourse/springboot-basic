package org.prgrms.assignment.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;


public interface Voucher {

    UUID getVoucherId();

    long getBenefit();

    VoucherType getVoucherType();

    LocalDateTime getCreatedAt();

    void setBenefit(long benefit);

    LocalDateTime getExpireDate();

}
