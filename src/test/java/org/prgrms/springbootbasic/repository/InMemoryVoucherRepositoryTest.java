package org.prgrms.springbootbasic.repository;

import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.voucher.PercentAmountVoucher;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryVoucherRepositoryTest {

    private final InMemoryVoucherRepository inMemoryVoucherRepository = new InMemoryVoucherRepository();


    @Test
    void insert() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(1000);
        inMemoryVoucherRepository.insert(fixedAmountVoucher);

        assertEquals(fixedAmountVoucher, inMemoryVoucherRepository.findById(fixedAmountVoucher.getVoucherId()).get());
    }

    @Test
    void findAll() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(1000);
        PercentAmountVoucher percentAmountVoucher = new PercentAmountVoucher(20);

        inMemoryVoucherRepository.insert(fixedAmountVoucher);
        inMemoryVoucherRepository.insert(percentAmountVoucher);

        assertEquals(inMemoryVoucherRepository.findAll().get(fixedAmountVoucher.getVoucherId()), fixedAmountVoucher);
        assertEquals(inMemoryVoucherRepository.findAll().size(), 2);
    }
}