package org.prgms.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;


class MemoryVoucherRepositoryTest {
    final Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10L);
    final Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);


    MemoryVoucherRepository memoryVoucherRepository;

    @BeforeEach
    void setUp() {
        memoryVoucherRepository = new MemoryVoucherRepository();
    }


    @Test
    @Order(1)
    @DisplayName("fixedVoucher를 추가할 수 있다. ")
    public void testFixedInsert() {
        var fixedVoucher = memoryVoucherRepository.save(fixedAmountVoucher);
        var retrievedVoucher = memoryVoucherRepository.findById(fixedVoucher.getVoucherId());
        assertThat(retrievedVoucher.isEmpty(), is(false));
    }

    @Test
    @Order(2)
    @DisplayName("percentVoucher를 추가할 수 있다. ")
    public void testPercentInsert() {
        var percentVoucher = memoryVoucherRepository.save(percentDiscountVoucher);
        var retrievedVoucher = memoryVoucherRepository.findById(percentVoucher.getVoucherId());
        assertThat(retrievedVoucher.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("전체 고객을 조회할 수 있다")
    public void testFindAll() {
        var percentVoucher = memoryVoucherRepository.save(percentDiscountVoucher);
        var fixedVoucher = memoryVoucherRepository.save(fixedAmountVoucher);
        var vouchers = memoryVoucherRepository.findAll();
        System.out.println(vouchers);
        assertThat(vouchers.isEmpty(), is(false));
    }


}
