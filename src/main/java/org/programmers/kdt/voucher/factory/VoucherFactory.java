package org.programmers.kdt.voucher.factory;

import org.programmers.kdt.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
// TODO: change to Abstract Factory Pattern
public abstract class VoucherFactory {
    abstract public Voucher createVoucher(UUID voucherId, long discount);
}
