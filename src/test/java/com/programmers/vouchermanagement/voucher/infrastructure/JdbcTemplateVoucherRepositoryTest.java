package com.programmers.vouchermanagement.voucher.infrastructure;

import com.programmers.vouchermanagement.voucher.domain.FixedAmountDiscountPolicy;
import com.programmers.vouchermanagement.voucher.domain.PercentDiscountPolicy;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class JdbcTemplateVoucherRepositoryTest {

    @Autowired
    JdbcTemplateVoucherRepository voucherRepository;

    @Test
    @DisplayName("바우처를 저장한다.")
    void save() {
        // given
        Voucher voucher = new Voucher(new FixedAmountDiscountPolicy(5000));

        // when
        voucherRepository.save(voucher);

        // then
        Voucher result = voucherRepository.findById(voucher.getId()).get();
        assertThat(result).isEqualTo(voucher);
    }

    @Test
    @DisplayName("Id로 바우처를 조회한다.")
    void findById() {
        // given
        Voucher voucher = new Voucher(new FixedAmountDiscountPolicy(5000));
        voucherRepository.save(voucher);

        // when
        Voucher result = voucherRepository.findById(voucher.getId()).get();

        // then
        assertThat(result).isEqualTo(voucher);
    }

    @Test
    @DisplayName("바우처를 모두 조회한다.")
    void findAll() {
        // given
        Voucher voucher1 = new Voucher(new FixedAmountDiscountPolicy(5000));
        Voucher voucher2 = new Voucher(new PercentDiscountPolicy(10));

        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);

        // when
        List<Voucher> result = voucherRepository.findAll();

        // then
        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("바우처를 업데이트한다.")
    void update() {
        // given
        Voucher voucher = new Voucher(new FixedAmountDiscountPolicy(5000));
        voucherRepository.save(voucher);
        voucher.changeDiscountPolicy(new PercentDiscountPolicy(10));

        // when
        voucherRepository.update(voucher);

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
        voucherRepository.deleteById(voucher.getId());

        // then
        Optional<Voucher> result = voucherRepository.findById(voucher.getId());
        assertThat(result).isEmpty();
    }
}