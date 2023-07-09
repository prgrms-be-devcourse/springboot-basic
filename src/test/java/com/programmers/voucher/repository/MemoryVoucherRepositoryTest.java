package com.programmers.voucher.repository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.programmers.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.Voucher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
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

    @DisplayName("바우처를 id로 조회한다")
    @Test
    void findById() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName", 10L);
        memoryVoucherRepository.save(fixedAmountVoucher);

        //when
        Optional<Voucher> result = memoryVoucherRepository.findById(fixedAmountVoucher.getVoucherId());

        //then
        assertThat(result.get().getVoucherId(), is(fixedAmountVoucher.getVoucherId()));
    }

    @DisplayName("바우처를 id로 조회했을 때 존재하지 않으면 예외처리한다")
    @Test
    void findByIdException() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName", 10L);

        //when
        //then
        assertThatThrownBy(() -> memoryVoucherRepository.findById(fixedAmountVoucher.getVoucherId()))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("바우처를 수정한다")
    @Test
    void update() {
        //given
        UUID id = UUID.randomUUID();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(id, "testName", 10L);
        memoryVoucherRepository.save(fixedAmountVoucher);

        //when
        Voucher result = memoryVoucherRepository.update(new FixedAmountVoucher(id, "update", 20L));

        //then
        assertThat(result.getVoucherId(), is(id));
        assertThat(result.getVoucherName(), is("update"));
        assertThat(result.getVoucherValue(), is(20L));
    }

    @DisplayName("id로 바우처를 삭제한다")
    @Test
    void deleteById() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName", 10L);
        memoryVoucherRepository.save(fixedAmountVoucher);

        //when
        memoryVoucherRepository.deleteById(fixedAmountVoucher.getVoucherId());
        List<Voucher> result = memoryVoucherRepository.findAll();

        //then
        Assertions.assertThat(result).isEmpty();
    }

    @DisplayName("저장된 모든 바우처들을 삭제한다")
    @Test
    void deleteAll() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName1", 10L);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), "testName2", 20L);

        memoryVoucherRepository.save(fixedAmountVoucher);
        memoryVoucherRepository.save(percentDiscountVoucher);

        //when
        memoryVoucherRepository.deleteAll();
        List<Voucher> result = memoryVoucherRepository.findAll();

        //then
        Assertions.assertThat(result).isEmpty();
    }
}