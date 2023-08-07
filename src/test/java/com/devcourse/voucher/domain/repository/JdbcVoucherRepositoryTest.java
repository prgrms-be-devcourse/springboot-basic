package com.devcourse.voucher.domain.repository;

import com.devcourse.voucher.domain.Voucher;
import com.devcourse.voucher.domain.Voucher.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.devcourse.voucher.domain.Voucher.Status.*;
import static com.devcourse.voucher.domain.Voucher.Type.*;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("dev")
@JdbcTest
@ContextConfiguration(classes = JdbcVoucherRepository.class)
class JdbcVoucherRepositoryTest {
    @Autowired
    private VoucherRepository voucherRepository;

    private final LocalDateTime expiredAt = LocalDateTime.now();

    @ParameterizedTest
    @DisplayName("바우처를 저장하면 생성 시 입력한 값들이 온전히 저장되어야 한다.")
    @EnumSource(Type.class)
    void saveTest(Type type) {
        // given
        int discount = 500;
        Voucher voucher = new Voucher(discount, expiredAt, type);

        // when
        voucherRepository.save(voucher);

        // then
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers).isNotEmpty().hasSize(1);

        Voucher saved = vouchers.get(0);
        assertThat(saved.id()).isNotNull();
        assertThat(saved.discount()).isEqualTo(discount);
        assertThat(saved.expireAt()).isEqualTo(expiredAt);
        assertThat(saved.type()).isEqualTo(type);
        assertThat(saved.isUsed()).isFalse();
    }

    @Nested
    @DisplayName("아이디로 조회하기 테스트")
    class findByIdTest {
        private final int discount = 50;
        private final Type percent = PERCENT;
        private final Voucher saved = voucherRepository.save(new Voucher(discount, expiredAt, percent));

        @Test
        @DisplayName("id를 통해서 조회한 바우처는 저장한 상태를 그대로 가지고 있어야 한다.")
        void findByIdSuccessTest() {
            // given

            // when
            Optional<Voucher> optionalVoucher = voucherRepository.findById(saved.id());

            // then
            assertThat(optionalVoucher).isNotEmpty();

            Voucher voucher = optionalVoucher.get();
            assertThat(voucher.id()).isEqualTo(saved.id());
            assertThat(voucher.discount()).isEqualTo(discount);
            assertThat(voucher.type()).isEqualTo(percent);
        }

        @Test
        @DisplayName("저장되지 않은 id로 조회하면 빈값이 반환된다.")
        void findByIdFailTest() {
            // given
            UUID noneId = UUID.randomUUID();

            // when
            Optional<Voucher> optionalVoucher = voucherRepository.findById(noneId);

            // then
            assertThat(optionalVoucher).isEmpty();
        }
    }

    @Test
    @DisplayName("삭제한 바우처는 아무것도 조회되지 않아야 한다.")
    void deleteByIdTest() {
        // given
        Voucher voucher = new Voucher(100, expiredAt, PERCENT);
        voucherRepository.save(voucher);

        // when
        voucherRepository.deleteById(voucher.id());

        // then
        Optional<Voucher> optionalVoucher = voucherRepository.findById(voucher.id());
        assertThat(optionalVoucher).isEmpty();
    }

    @Test
    @DisplayName("바우처의 상태를 수정하면 수정된 상태가 잘 반영되어야 한다.")
    void updateStatusTest() {
        // given
        Voucher voucher = new Voucher(100, expiredAt, PERCENT);
        voucherRepository.save(voucher);

        // when
        voucherRepository.updateStatus(voucher.id(), USED.name());

        // then
        Optional<Voucher> optionalVoucher = voucherRepository.findById(voucher.id());
        assertThat(optionalVoucher).isNotEmpty();
        assertThat(optionalVoucher.get().isUsed()).isTrue();
    }

}
