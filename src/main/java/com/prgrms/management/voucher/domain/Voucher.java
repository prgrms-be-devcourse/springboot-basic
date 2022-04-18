package com.prgrms.management.voucher.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Voucher {
    private UUID voucherId;
    private Long amount;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private VoucherType voucherType;
    private UUID customerId;

    public Voucher(UUID voucherId, Long amount, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherType = voucherType;
    }

    public Voucher(UUID customerId) {
        this.customerId = customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }
}