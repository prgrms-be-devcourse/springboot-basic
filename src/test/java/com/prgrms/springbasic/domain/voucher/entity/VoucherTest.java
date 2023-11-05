package com.prgrms.springbasic.domain.voucher.entity;

import com.prgrms.springbasic.common.exception.InvalidValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoucherTest {

    @Test
    @DisplayName("voucher 타입별 생성 테스트")
    void testCreateVoucher() {
        Voucher fixedVoucher = Voucher.createVoucher(UUID.randomUUID(), "fixed", 50, LocalDateTime.now());
        Voucher percentVoucher = Voucher.createVoucher(UUID.randomUUID(), "percent", 50, LocalDateTime.now());

        assertThat(fixedVoucher).isInstanceOf(FixedAmountVoucher.class);
        assertThat(percentVoucher).isInstanceOf(PercentDiscountVoucher.class);
    }

    @Test
    @DisplayName("percent 타입의 discountValue는 0부터 100 사이의 값이어야 한다")
    void testPercentVoucherCreationFail() {
        assertThatThrownBy(() -> Voucher.createVoucher(UUID.randomUUID(), "percent", 150, LocalDateTime.now()))
                .isInstanceOf(InvalidValueException.class);
    }

    @Test
    @DisplayName("할인 금액은 마이너스가 될 수 없다")
    void testDiscountWithMinus() {
        assertThatThrownBy(() -> Voucher.createVoucher(UUID.randomUUID(), "fixed", -100, LocalDateTime.now()))
                .isInstanceOf(InvalidValueException.class);
    }
}
