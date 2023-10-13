package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.mock.repository.MockVoucherRepository;
import com.programmers.springbootbasic.mock.util.MockUuidProvider;
import org.junit.jupiter.api.Test;

class VoucherServiceTest {
    private final VoucherService voucherService = new VoucherService(new MockVoucherRepository(), new MockUuidProvider());

    @Test
    void create() {
    }

    @Test
    void getAll() {
    }
}