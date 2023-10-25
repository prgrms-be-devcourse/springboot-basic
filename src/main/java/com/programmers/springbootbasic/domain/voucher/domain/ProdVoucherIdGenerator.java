package com.programmers.springbootbasic.domain.voucher.domain;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ProdVoucherIdGenerator implements VoucherIdGenerator {

    @Override
    public UUID generate() {
        return UUID.randomUUID();
    }
}
