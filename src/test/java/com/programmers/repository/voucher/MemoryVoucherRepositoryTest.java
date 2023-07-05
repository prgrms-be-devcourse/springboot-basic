package com.programmers.repository.voucher;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.programmers.domain.voucher.FixedAmountVoucher;
import com.programmers.domain.voucher.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class MemoryVoucherRepositoryTest {

    MemoryVoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();

    @DisplayName("바우처를 저장한다")
    @Test
    void save() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "voucherName1", 10L);

        //when
        memoryVoucherRepository.save(fixedAmountVoucher);
        Voucher result = memoryVoucherRepository.findAll().stream().findFirst().get();

        //then
        assertThat(result, is(fixedAmountVoucher));
    }

    @DisplayName("저장된 바우처를 조회한다")
    @Test
    void findAll() {
        //given
        FixedAmountVoucher f1 = new FixedAmountVoucher(UUID.randomUUID(), "voucherName1", 11L);
        memoryVoucherRepository.save(f1);
        FixedAmountVoucher f2 = new FixedAmountVoucher(UUID.randomUUID(), "voucherName2", 12L);
        memoryVoucherRepository.save(f2);

        //when
        List<Voucher> result = memoryVoucherRepository.findAll();

        //then
        assertThat(result, hasItem(f1));
        assertThat(result, hasItem(f2));
    }
}