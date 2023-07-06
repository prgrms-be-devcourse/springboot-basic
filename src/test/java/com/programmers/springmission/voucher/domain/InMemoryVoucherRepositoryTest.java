package com.programmers.springmission.voucher.domain;

import com.programmers.springmission.global.exception.ErrorMessage;
import com.programmers.springmission.global.exception.InvalidInputException;
import com.programmers.springmission.voucher.domain.enums.VoucherType;
import com.programmers.springmission.voucher.repository.InMemoryVoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class InMemoryVoucherRepositoryTest {

    InMemoryVoucherRepository repository;

    @BeforeEach
    void beforeEach() {
        repository = new InMemoryVoucherRepository();
    }

    @DisplayName("Voucher 가 repository 에 저장 성공하는지 테스트")
    @Test
    void repository_save_success() {
        // given
        Voucher voucher1 = new Voucher(new FixedAmountPolicy(VoucherType.FIXED_AMOUNT), 10L);
        Voucher voucher2 = new Voucher(new PercentDiscountPolicy(VoucherType.PERCENT_DISCOUNT), 10L);

        // when
        repository.save(voucher1);
        repository.save(voucher2);

        // then
        List<Voucher> all = repository.findAll();

        assertThat(all.size()).isEqualTo(2);
        assertThat(all).contains(voucher1, voucher2);
    }

    @DisplayName("Voucher 수정 성공하는지 테스트")
    @Test
    void repository_update_success() {
        // given
        Voucher voucher1 = new Voucher(new FixedAmountPolicy(VoucherType.FIXED_AMOUNT), 10L);
        repository.save(voucher1);

        // when
        voucher1.updateAmount(50L);
        repository.update(voucher1);

        // then
        List<Voucher> all = repository.findAll();
        assertThat(all.get(0).getVoucherId()).isEqualTo(voucher1.getVoucherId());
    }

    @DisplayName("Voucher 수정 실패하는지 테스트")
    @Test
    void repository_update_fail() {
        // given
        Voucher voucher1 = new Voucher(new FixedAmountPolicy(VoucherType.FIXED_AMOUNT), 10L);
        Voucher voucher2 = new Voucher(new PercentDiscountPolicy(VoucherType.PERCENT_DISCOUNT), 10L);

        // when
        repository.save(voucher1);

        // then
        assertThatThrownBy(() -> repository.update(voucher2))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(ErrorMessage.NOT_EXIST_VOUCHER.getMessage());
    }

    @DisplayName("Voucher 단건 삭제 성공하는지 테스트")
    @Test
    void repository_delete_byId_success() {
        // given
        Voucher voucher1 = new Voucher(new FixedAmountPolicy(VoucherType.FIXED_AMOUNT), 10L);
        Voucher voucher2 = new Voucher(new PercentDiscountPolicy(VoucherType.PERCENT_DISCOUNT), 10L);

        // when
        repository.save(voucher1);
        repository.save(voucher2);
        repository.deleteById(voucher2.getVoucherId());

        // then
        List<Voucher> all = repository.findAll();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0).getVoucherId()).isEqualTo(voucher1.getVoucherId());
    }

    @DisplayName("Voucher 단건 삭제 실패하는지 테스트")
    @Test
    void repository_delete_byId_fail() {
        // given
        Voucher voucher1 = new Voucher(new FixedAmountPolicy(VoucherType.FIXED_AMOUNT), 10L);
        Voucher voucher2 = new Voucher(new PercentDiscountPolicy(VoucherType.PERCENT_DISCOUNT), 10L);

        // when
        repository.save(voucher1);

        // then
        assertThatThrownBy(() -> repository.deleteById(voucher2.getVoucherId()))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(ErrorMessage.NOT_EXIST_VOUCHER.getMessage());
    }

    @DisplayName("Voucher 전부 삭제 성공하는지 테스트")
    @Test
    void repository_delete_all_success() {
        // given
        Voucher voucher1 = new Voucher(new FixedAmountPolicy(VoucherType.FIXED_AMOUNT), 10L);
        Voucher voucher2 = new Voucher(new PercentDiscountPolicy(VoucherType.PERCENT_DISCOUNT), 10L);

        // when
        repository.save(voucher1);
        repository.save(voucher2);
        repository.deleteAll();

        // then
        List<Voucher> all = repository.findAll();
        assertThat(all.size()).isEqualTo(0);
    }
}

