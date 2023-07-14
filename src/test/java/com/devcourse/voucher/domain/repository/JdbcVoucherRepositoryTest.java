package com.devcourse.voucher.domain.repository;

import com.devcourse.voucher.domain.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("dev")
@JdbcTest
@ContextConfiguration(classes = JdbcVoucherRepository.class)
class JdbcVoucherRepositoryTest {
    @Autowired
    private VoucherRepository voucherRepository;

    @ParameterizedTest
    @DisplayName("바우처를 저장하면 생성 시 입력한 값들이 온전히 저장되어야 한다.")
    @EnumSource(Voucher.Type.class)
    void saveTest(Voucher.Type type) {
        // given
        int discount = 500;
        LocalDateTime expiredAt = LocalDateTime.now();
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
}
