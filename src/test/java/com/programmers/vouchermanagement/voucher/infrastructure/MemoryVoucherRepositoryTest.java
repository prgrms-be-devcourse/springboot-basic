package com.programmers.vouchermanagement.voucher.infrastructure;

import com.programmers.vouchermanagement.voucher.domain.FixedAmountDiscountPolicy;
import com.programmers.vouchermanagement.voucher.domain.PercentDiscountPolicy;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryVoucherRepositoryTest {

    MemoryVoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();

    @AfterEach
    void afterEach() {
        memoryVoucherRepository.clearStorage();
    }

    @Test
    @DisplayName("바우처를 저장한다.")
    void save() {
        // given
        Voucher voucher = new Voucher(new FixedAmountDiscountPolicy(5000));

        // when
        memoryVoucherRepository.save(voucher);

        // then
        Voucher result = memoryVoucherRepository.findById(voucher.getId()).get();
        assertThat(result).isEqualTo(voucher);
    }

    @Test
    @DisplayName("Id로 바우처를 조회한다.")
    void findById() {
        // given
        UUID id = UUID.randomUUID();
        Voucher voucher = new Voucher(id, new FixedAmountDiscountPolicy(5000));
        memoryVoucherRepository.save(voucher);

        // when
        Voucher result = memoryVoucherRepository.findById(voucher.getId()).get();

        // then
        assertThat(result).isEqualTo(voucher);
    }

    @Test
    @DisplayName("바우처를 모두 조회한다.")
    void findAll() {
        // given
        Voucher voucher1 = new Voucher(new FixedAmountDiscountPolicy(5000));
        Voucher voucher2 = new Voucher(new PercentDiscountPolicy(10));

        memoryVoucherRepository.save(voucher1);
        memoryVoucherRepository.save(voucher2);

        // when
        List<Voucher> result = memoryVoucherRepository.findAll();

        // then
        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("바우처를 업데이트한다.")
    void update() {
        // given
        Voucher voucher = new Voucher(new FixedAmountDiscountPolicy(5000));
        memoryVoucherRepository.save(voucher);
        voucher.changeDiscountPolicy(new PercentDiscountPolicy(10));

        // when
        memoryVoucherRepository.update(voucher);

        // then
        assertThat(voucher.getDiscountPolicy()).isInstanceOf(PercentDiscountPolicy.class);
        assertThat(voucher.getDiscountPolicy().getAmount()).isEqualTo(10);
    }

    @Test
    @DisplayName("바우처를 삭제한다.")
    void deleteById() {
        // given
        Voucher voucher = new Voucher(new FixedAmountDiscountPolicy(5000));

        // when
        memoryVoucherRepository.deleteById(voucher.getId());

        // then
        Optional<Voucher> result = memoryVoucherRepository.findById(voucher.getId());
        assertThat(result).isEmpty();
    }
}