package org.prgrms.kdt.domain.voucher.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("고정할인 바우처가 정상적으로 생성된다.")
    public void createFixedAmountVoucher(){
        //given
        UUID voucherId = UUID.randomUUID();
        long discountPrice = 10000;
        LocalDateTime now = LocalDateTime.now();
        //when
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(voucherId,discountPrice, now, now);
        //then
        assertThat(fixedAmountVoucher)
                .extracting(FixedAmountVoucher::getVoucherId)
                .isEqualTo(voucherId);
        assertThat(fixedAmountVoucher)
                .extracting(FixedAmountVoucher::getDiscountValue)
                .isEqualTo(discountPrice);
    }

    @Test
    @DisplayName("할인금액이 0원 이하일 경우 고정할인 바우처가 생성되지 않고 예외가 발생한다.")
    public void exception_createFixedAmountVoucher(){
        //given
        UUID voucherId = UUID.randomUUID();
        long discountPrice = 0;
        LocalDateTime now = LocalDateTime.now();
        //when
        //then
        assertThatThrownBy(() -> new FixedAmountVoucher(voucherId,discountPrice, now, now))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인금액은 0원 이하가 될 수 없습니다.");
    }
}