package org.programers.vouchermanagement.voucher.domain;

import org.junit.jupiter.api.*;
import org.programers.vouchermanagement.global.exception.NoSuchDomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class JdbcVoucherRepositoryTest {

    @Autowired
    private VoucherRepository voucherRepository;

    private Voucher voucher;

    @Order(1)
    @Test
    void 바우처를_저장한다() {
        // given
        voucher = new Voucher(new FixedAmountPolicy(100), VoucherType.FIXED_AMOUNT);

        // when
        Voucher result = voucherRepository.save(voucher);

        // then
        assertThat(result.getId()).isEqualTo(voucher.getId());
    }

    @Order(2)
    @Test
    void 바우처를_아이디로_조회한다() {
        // given & when
        Voucher result = voucherRepository.getById(voucher.getId());

        // then
        assertThat(result.getId()).isEqualTo(voucher.getId());
    }

    @Order(3)
    @Test
    void 바우처를_모두_조회한다() {
        // given
        voucherRepository.save(new Voucher(new FixedAmountPolicy(10), VoucherType.FIXED_AMOUNT));

        // when
        List<Voucher> result = voucherRepository.findAll();

        // then
        assertThat(result).hasSize(2);
    }

    @Order(4)
    @Test
    void 바우처를_수정한다() {
        // given
        Voucher updateVoucher = new Voucher(voucher.getId(), new PercentDiscountPolicy(10), VoucherType.PERCENT);

        // when
        voucherRepository.update(updateVoucher);

        // then
        assertThat(voucherRepository.getById(voucher.getId()).getType()).isEqualTo(VoucherType.PERCENT);
    }

    @Order(5)
    @Test
    void 바우처를_삭제한다() {
        // given & when
        voucherRepository.deleteById(voucher.getId());

        // then
        assertThatThrownBy(() -> {
            voucherRepository.getById(voucher.getId());
        })
                .isInstanceOf(NoSuchDomainException.class)
                .hasMessage("존재하지 않는 바우처입니다.");
    }
}
