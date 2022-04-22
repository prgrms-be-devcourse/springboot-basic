package org.programmers.springbootbasic.domain;

import org.programmers.springbootbasic.voucher.domain.AbstractVoucher;
import org.programmers.springbootbasic.voucher.domain.VoucherType;

import java.util.UUID;

public class TestVoucher extends AbstractVoucher {

    public TestVoucher(UUID id, int amount, VoucherType type) {
        super(id, amount, type);
    }

    @Override
    public long discount(long beforeDiscount) {
        return 0;
    }
}