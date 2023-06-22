package com.programmers.repository;

import com.programmers.domain.FixedAmountVoucher;
import com.programmers.domain.Voucher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
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
        Assertions.assertThat(result).isEqualTo(fixedAmountVoucher);
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
        List<Voucher> expected = Arrays.asList(f1, f2);
        List<Voucher> result = memoryVoucherRepository.findAll();

        //then
        Assertions.assertThat(result).contains(f1);
        Assertions.assertThat(result).contains(f2);
    }
}