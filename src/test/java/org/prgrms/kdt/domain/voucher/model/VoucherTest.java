package org.prgrms.kdt.domain.voucher.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoucherTest {

    @Test
    @DisplayName("정률할인 바우처가 정상적으로 생성된다.")
    void createPercentDiscountVoucher(){
        //given
        UUID voucherId = UUID.randomUUID();
        long discountRate = 20;
        //when
        Voucher percentDiscountVoucher = new Voucher(
                voucherId, VoucherType.PERCENT_DISCOUNT, discountRate, LocalDateTime.now(), LocalDateTime.now());
        //then
        assertThat(percentDiscountVoucher)
                .extracting(Voucher::getVoucherId)
                .isEqualTo(voucherId);
        assertThat(percentDiscountVoucher)
                .extracting(Voucher::getDiscountValue)
                .isEqualTo(discountRate);
    }

    @Test
    @DisplayName("고정할인 바우처가 정상적으로 생성된다.")
    void createFixedAmountVoucher(){
        //given
        UUID voucherId = UUID.randomUUID();
        long discountPrice = 10000;
        LocalDateTime now = LocalDateTime.now();
        //when
        Voucher fixedAmountVoucher = new Voucher(voucherId,VoucherType.FIXED_AMOUNT, discountPrice, now, now);
        //then
        assertThat(fixedAmountVoucher)
                .extracting(Voucher::getVoucherId)
                .isEqualTo(voucherId);
        assertThat(fixedAmountVoucher)
                .extracting(Voucher::getDiscountValue)
                .isEqualTo(discountPrice);
    }

    @Test
    @DisplayName("할인금액이 0원 이하일 경우 고정할인 바우처가 생성되지 않고 예외가 발생한다.")
    public void createFixedAmountVoucher_exception(){
        //given
        UUID voucherId = UUID.randomUUID();
        long discountPrice = 0;
        LocalDateTime now = LocalDateTime.now();
        //when
        //then
        assertThatThrownBy(() -> new Voucher(voucherId,VoucherType.FIXED_AMOUNT, discountPrice, now, now))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인금액은 0원 이하가 될 수 없습니다.");
    }

    @Test
    @DisplayName("할인률이 0퍼센트 이하 혹은 100퍼센트 초과일 경우 정률할인 바우처가 생성되지 않고 예외가 발생한다.")
    public void createPercentDiscountVoucher_exception(){
        //given
        UUID voucherId = UUID.randomUUID();
        long underDiscountRate = 0;
        long overDiscountRate = 101;
        LocalDateTime now = LocalDateTime.now();
        //when
        //then
        assertThatThrownBy(() -> new Voucher(voucherId,VoucherType.PERCENT_DISCOUNT, underDiscountRate, now, now))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 0퍼센트 이하이거나 100퍼센트를 초과할 수 없습니다.");
        assertThatThrownBy(() -> new Voucher(voucherId,VoucherType.PERCENT_DISCOUNT, overDiscountRate, now, now))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 0퍼센트 이하이거나 100퍼센트를 초과할 수 없습니다.");
    }
}
