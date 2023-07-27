package org.devcourse.springbasic.domain.voucher.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public abstract class Voucher {

    private final UUID voucherId;
    private final long discountRate;
    private final VoucherType voucherType;
    private final LocalDateTime createdAt;

    public abstract long discount(long beforeDiscount);
    public abstract void validate();
}
