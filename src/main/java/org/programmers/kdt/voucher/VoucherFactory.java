package org.programmers.kdt.voucher;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public abstract class VoucherFactory {
    abstract public Voucher createVoucher(UUID voucherId, long discount);
}
