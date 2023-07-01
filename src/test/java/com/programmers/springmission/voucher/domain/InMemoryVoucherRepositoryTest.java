package com.programmers.springmission.voucher.domain;

import com.programmers.springmission.voucher.domain.enums.VoucherType;
import com.programmers.springmission.voucher.repository.InMemoryVoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryVoucherRepositoryTest {

    InMemoryVoucherRepository repository;

    @BeforeEach
    void beforeEach() {
        repository = new InMemoryVoucherRepository();
    }

    @DisplayName("Voucher 가 repository 에 저장 성공하는지 테스트")
    @Test
    void repository_right_work_success() {
        // given
        Voucher voucher1 = new Voucher(new FixedAmountPolicy(10L), VoucherType.FIXED_AMOUNT);
        Voucher voucher2 = new Voucher(new PercentDiscountPolicy(10L), VoucherType.PERCENT_DISCOUNT);

        // when
        repository.save(voucher1);
        repository.save(voucher2);

        // then
        List<Voucher> all = repository.findAll();

        assertThat(all.size()).isEqualTo(2);
        assertThat(all).contains(voucher1, voucher2);
    }
}

