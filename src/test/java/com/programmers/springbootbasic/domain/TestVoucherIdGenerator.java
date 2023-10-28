package com.programmers.springbootbasic.domain;

import com.programmers.springbootbasic.domain.voucher.domain.VoucherIdGenerator;
import java.util.UUID;

public class TestVoucherIdGenerator implements VoucherIdGenerator {

    @Override
    public UUID generate() {
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }
}
