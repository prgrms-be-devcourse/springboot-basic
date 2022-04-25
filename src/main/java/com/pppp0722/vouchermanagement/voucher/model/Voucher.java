package com.pppp0722.vouchermanagement.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    VoucherType getType();

    long getAmount();

    LocalDateTime getCreatedAt();

    UUID getMemberId();
}