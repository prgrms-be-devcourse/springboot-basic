package org.devcourse.voucher.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class VoucherTest {

    @Test
    @DisplayName("지정된 정책에 맞게 할인을 적용한다 - 고정 할인 가")
    void executeFixedDiscountPolicy() {
        Voucher voucher = new Voucher(1, VoucherType.FIX, 10);

        int result = voucher.retrieveBalance(1000);

        assertThat(result).isEqualTo(990);
    }

    @Test
    @DisplayName("지정된 정책에 맞게 할인을 적용한다 - 퍼센트 할인 가")
    void executePercentDiscountPolicy() {
        Voucher voucher = new Voucher(1, VoucherType.PERCENT, 10);

        int result = voucher.retrieveBalance(1000);

        assertThat(result).isEqualTo(900);
    }

    @Test
    @DisplayName("바우처 타입을 지정하지 않아 생성에 실패한다")
    void failCreateVoucherWithNoType() {
        assertThatThrownBy(() -> new Voucher(1, null, 1))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("바우처 타입은 빈 값일 수 없습니다");
    }

}
