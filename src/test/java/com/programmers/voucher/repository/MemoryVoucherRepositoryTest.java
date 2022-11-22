package com.programmers.voucher.repository;

import com.programmers.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.Voucher;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemoryVoucherRepositoryTest {

    @Autowired
    MemoryVoucherRepository memoryVoucherRepository;

    Voucher newVoucher;
    @BeforeAll
    void setup() {
        newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 5000);
        memoryVoucherRepository.insert(newVoucher);
    }

    @Test
    @Order(1)
    void testInsert() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        memoryVoucherRepository.insert(voucher);

        var insertedVoucher = memoryVoucherRepository.findByID(voucher.getVoucherId());
        assertThat(insertedVoucher.isEmpty(), is(false));
        assertThat(insertedVoucher.get(), samePropertyValuesAs(voucher));
    }

    @Test
    @Order(2)
    void findByID() {
        var retrievedVoucher = memoryVoucherRepository.findByID(newVoucher.getVoucherId());
        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(newVoucher));
    }


    @Test
    void findAll() {
        var vouchers = memoryVoucherRepository.findAll();
        assertThat(vouchers.size(), is(2));
    }
}