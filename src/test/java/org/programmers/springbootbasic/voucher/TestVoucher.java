package org.programmers.springbootbasic.voucher;

import lombok.RequiredArgsConstructor;

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