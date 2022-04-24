package com.prgrms.management.voucher.domain;

import com.prgrms.management.config.ErrorMessageType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Voucher {
    private static final Long MAX_FIXED_DISCOUNT = 10000L;
    private static final Long MAX_PERCENT_DISCOUNT = 100L;
    private static final Long MIN_DISCOUNT = 0L;
    private UUID voucherId;
    private Long amount;
    private LocalDateTime createdAt;
    private VoucherType voucherType;
    private UUID customerId = null;

    public Voucher(Long amount, VoucherType voucherType) {
        if (voucherType.equals(VoucherType.FIXED))
            validateFixedAmount(amount);
        else
            validatePercentAmount(amount);
        this.voucherId = UUID.randomUUID();
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
        this.voucherType = voucherType;
        this.customerId = null;
    }

    public Voucher(UUID voucherId, Long amount, LocalDateTime createdAt, VoucherType voucherType, UUID customerId) {
        if (voucherType.equals(VoucherType.FIXED))
            validateFixedAmount(amount);
        else
            validatePercentAmount(amount);
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = createdAt;
        this.voucherType = voucherType;
        this.customerId = customerId;
    }

    private void validateFixedAmount(long amount) {
        if (amount < MIN_DISCOUNT || amount > MAX_FIXED_DISCOUNT)
            throw new NumberFormatException(VoucherType.class + ErrorMessageType.OUT_OF_RANGE_FIXED_NUMBER.getMessage());
    }

    private void validatePercentAmount(long amount) {
        if (amount < MIN_DISCOUNT || amount > MAX_PERCENT_DISCOUNT)
            throw new NumberFormatException(VoucherType.class + ErrorMessageType.OUT_OF_RANGE_PERCENT_NUMBER.getMessage());
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

}