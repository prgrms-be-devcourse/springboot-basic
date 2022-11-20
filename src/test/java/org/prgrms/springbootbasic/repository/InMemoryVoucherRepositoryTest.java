package org.prgrms.springbootbasic.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.entity.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.PercentAmountVoucher;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryVoucherRepositoryTest {

    private final InMemoryVoucherRepository inMemoryVoucherRepository = new InMemoryVoucherRepository();

    @Test
    @DisplayName("생성 성공")
    void insert() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        inMemoryVoucherRepository.insert(fixedAmountVoucher);

        assertEquals(fixedAmountVoucher, inMemoryVoucherRepository.findById(fixedAmountVoucher.getVoucherId()).get());
    }

    @Test
    @DisplayName("조회 성공")
    void findAll() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        PercentAmountVoucher percentAmountVoucher = new PercentAmountVoucher(UUID.randomUUID(), 20);

        inMemoryVoucherRepository.insert(fixedAmountVoucher);
        inMemoryVoucherRepository.insert(percentAmountVoucher);

        assertTrue(inMemoryVoucherRepository
                .findAll().stream()
                .anyMatch(voucher -> voucher.getVoucherId()
                        .equals(fixedAmountVoucher.getVoucherId())
                )
        );

        assertTrue(inMemoryVoucherRepository
                .findAll().stream()
                .anyMatch(voucher -> voucher.getVoucherId()
                        .equals(percentAmountVoucher.getVoucherId())
                )
        );

        assertEquals(inMemoryVoucherRepository.findAll().size(), 2);
    }
}