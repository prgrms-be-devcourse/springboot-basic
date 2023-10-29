package com.programmers.vouchermanagement.voucher;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.exception.IllegalDiscountException;
import com.programmers.vouchermanagement.voucher.repository.JdbcVoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class JdbcVoucherRepositoryTest {

    @Autowired
    private JdbcVoucherRepository voucherRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void clear() {
        jdbcTemplate.update("DELETE FROM voucher");
    }

    @Test
    @DisplayName("Voucher를 저장할 수 있다.")
    void successSave() {

        // given
        Voucher voucher = new Voucher(UUID.randomUUID(), 10000L, VoucherType.FIXED, VoucherType.FIXED.getVoucherPolicy());

        // when
        voucherRepository.save(voucher);

        // then
        Voucher savedVoucher = voucherRepository.findById(voucher.getVoucherId()).get();
        assertThat(savedVoucher.getVoucherId()).isEqualTo(voucher.getVoucherId());
        assertThat(savedVoucher.getDiscount()).isEqualTo(voucher.getDiscount());
        assertThat(savedVoucher.getVoucherType()).isEqualTo(voucher.getVoucherType());
        assertThat(savedVoucher.getVoucherType().getVoucherPolicy()).isEqualTo(voucher.getVoucherType().getVoucherPolicy());
//        assertThat(savedVoucher).usingRecursiveComparison().isEqualTo(voucher);
    }

    @Test
    @DisplayName("모든 Voucher를 조회할 수 있다.")
    void successFindAll() {

        // given
        Voucher voucher1 = new Voucher(UUID.randomUUID(), 10000L, VoucherType.FIXED, VoucherType.FIXED.getVoucherPolicy());
        Voucher voucher2 = new Voucher(UUID.randomUUID(), 80L, VoucherType.PERCENT, VoucherType.PERCENT.getVoucherPolicy());
        Voucher voucher3 = new Voucher(UUID.randomUUID(), 500L, VoucherType.FIXED, VoucherType.FIXED.getVoucherPolicy());
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);

        // when
        List<Voucher> vouchers = voucherRepository.findAll();

        // then
        assertThat(vouchers).hasSize(3)
                .extracting(Voucher::getVoucherId)
                        .contains(voucher1.getVoucherId(), voucher2.getVoucherId(), voucher3.getVoucherId());
    }

    @Test
    @DisplayName("Voucher를 수정할 수 있다.")
    void successUpdate() {

        // given
        Voucher voucher = new Voucher(UUID.randomUUID(), 10000L, VoucherType.FIXED, VoucherType.FIXED.getVoucherPolicy());
        voucherRepository.save(voucher);

        // when
        Voucher newVoucher = new Voucher(voucher.getVoucherId(), 50L, VoucherType.PERCENT, VoucherType.PERCENT.getVoucherPolicy());
        voucherRepository.update(newVoucher);

        // then
        Voucher updatedVoucher = voucherRepository.findById(voucher.getVoucherId()).get();
        assertThat(updatedVoucher.getVoucherId()).isEqualTo(voucher.getVoucherId());
        assertThat(updatedVoucher.getDiscount()).isEqualTo(newVoucher.getDiscount());
        assertThat(updatedVoucher.getVoucherType()).isEqualTo(newVoucher.getVoucherType());
        assertThat(updatedVoucher.getVoucherType().getVoucherPolicy()).isEqualTo(newVoucher.getVoucherType().getVoucherPolicy());
    }

    @Test
    @DisplayName("모든 Voucher를 삭제할 수 있다.")
    void successDeleteAll() {

        // given
        Voucher voucher1 = new Voucher(UUID.randomUUID(), 10000L, VoucherType.FIXED, VoucherType.FIXED.getVoucherPolicy());
        Voucher voucher2 = new Voucher(UUID.randomUUID(), 80L, VoucherType.PERCENT, VoucherType.PERCENT.getVoucherPolicy());
        Voucher voucher3 = new Voucher(UUID.randomUUID(), 500L, VoucherType.FIXED, VoucherType.FIXED.getVoucherPolicy());
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);

        // when
        voucherRepository.deleteAll();

        // then
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers).hasSize(0);
    }

    @Test
    @DisplayName("올바르지 않은 할인 범위는 Voucher 생성 시 예외를 발생시킨다.")
    void failValidateDiscount() {

        assertThatThrownBy(() -> new Voucher(UUID.randomUUID(), 10000L, VoucherType.PERCENT, VoucherType.PERCENT.getVoucherPolicy()))
                .isInstanceOf(IllegalDiscountException.class);
    }

    @Test
    @DisplayName("올바르지 않은 아이디는 Voucher 조회 시 빈 Optional 객체를 생성한다.")
    void failFindById() {

        // given
        Voucher voucher = new Voucher(UUID.randomUUID(), 10000L, VoucherType.FIXED, VoucherType.FIXED.getVoucherPolicy());
        voucherRepository.save(voucher);

        // when
        Optional<Voucher> savedVoucher = voucherRepository.findById(UUID.randomUUID());

        // then
        assertThat(savedVoucher.isPresent()).isEqualTo(false);
    }
}
