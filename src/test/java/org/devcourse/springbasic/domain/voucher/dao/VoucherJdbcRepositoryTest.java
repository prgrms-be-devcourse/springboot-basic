package org.devcourse.springbasic.domain.voucher.dao;

import org.devcourse.springbasic.domain.voucher.domain.Voucher;
import org.devcourse.springbasic.domain.voucher.domain.VoucherFactory;
import org.devcourse.springbasic.domain.voucher.domain.VoucherType;
import org.devcourse.springbasic.global.exception.custom.DuplicateVoucherException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VoucherJdbcRepositoryTest {

    @Autowired
    private VoucherJdbcRepository voucherRepository;

    @TestConfiguration
    static class Config {
        @Bean
        public VoucherJdbcRepository voucherRepository(NamedParameterJdbcTemplate template) {
            return new VoucherJdbcRepository(template);
        }
    }

    private Voucher getDummyVoucher() {
        long discountRate = 1000L;
        VoucherType voucherType = VoucherType.FIXED_AMOUNT;
        return VoucherFactory.createVoucher(voucherType, discountRate);
    }

    @Test
    @DisplayName("바우처 추가 및 조회")
    void saveAndFindById() {
        // given
        Voucher voucher = getDummyVoucher();
        // when
        UUID savedVoucherId = voucherRepository.save(voucher);
        Optional<Voucher> foundVoucher = voucherRepository.findById(savedVoucherId);
        // then
        assertThat(savedVoucherId).isEqualTo(voucher.getVoucherId());
        assertThat(foundVoucher).isPresent();
        assertThat(foundVoucher.get().getVoucherId())
                .isEqualTo(voucher.getVoucherId());
    }

    @Test
    @DisplayName("중복된 바우처 추가 시, 예외 발생")
    void saveDuplicateVoucher() {
        // given
        Voucher voucher = getDummyVoucher();
        // when
        voucherRepository.save(voucher);
        Voucher duplicateVoucher = VoucherFactory.createVoucherWithParam(
                voucher.getVoucherId(),
                voucher.getDiscountRate(),
                voucher.getVoucherType(),
                LocalDateTime.now()
        );
        // then
        assertThatThrownBy(() -> voucherRepository.save(duplicateVoucher))
                .isInstanceOf(DuplicateVoucherException.class);
    }

    @Test
    @DisplayName("바우처 삭제")
    void deleteById() {
        // given
        Voucher voucher = getDummyVoucher();
        UUID voucherId = voucherRepository.save(voucher);
        // when
        voucherRepository.deleteById(voucherId);
        // then
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucherId);
        assertThat(foundVoucher).isEmpty();
    }

    @Test
    @DisplayName("없는 바우처 삭제 시도 시, 예외 발생")
    void deleteNonExistingVoucher() {
        // given
        UUID nonExistingVoucherId = UUID.randomUUID();
        // then
        assertThatThrownBy(() -> voucherRepository.deleteById(nonExistingVoucherId))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("바우처 검색")
    void findByCriteria() {
        // given
        Voucher voucher1 = VoucherFactory.createVoucher(VoucherType.FIXED_AMOUNT, 500L);
        Voucher voucher2 = VoucherFactory.createVoucher(VoucherType.PERCENT_DISCOUNT, 10L);
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        // when
        List<Voucher> foundVouchers = voucherRepository.findByCriteria(null, null, VoucherType.FIXED_AMOUNT);
        // then
        assertThat(foundVouchers).hasSize(1);
        assertThat(foundVouchers.get(0).getVoucherId())
                .isEqualTo(voucher1.getVoucherId());
    }
}