package org.prgrms.kdt.domain.voucher.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("정률할인 바우처가 정상적으로 생성된다.")
    public void createPercentDiscountVoucher(){
        //given
        UUID voucherId = UUID.randomUUID();
        long discountRate = 20;
        //when
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(voucherId, discountRate);
        //then
        assertThat(percentDiscountVoucher)
                .extracting(PercentDiscountVoucher::getVoucherId)
                .isEqualTo(voucherId);
        assertThat(percentDiscountVoucher)
                .extracting(PercentDiscountVoucher::getDiscountValue)
                .isEqualTo(discountRate);
    }

    @Test
    @DisplayName("할인률이 0퍼센트 이하 혹은 100퍼센트 초과일 경우 정률할인 바우처가 생성되지 않고 예외가 발생한다.")
    public void exception_createPercentDiscountVoucher(){
        //given
        UUID voucherId = UUID.randomUUID();
        long underDiscountRate = 0;
        long overDiscountRate = 101;
        //when
        //then
        assertThatThrownBy(() -> new PercentDiscountVoucher(voucherId,underDiscountRate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 0퍼센트 이하이거나 100퍼센트를 초과할 수 없습니다.");
        assertThatThrownBy(() -> new PercentDiscountVoucher(voucherId,overDiscountRate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 0퍼센트 이하이거나 100퍼센트를 초과할 수 없습니다.");
    }
}