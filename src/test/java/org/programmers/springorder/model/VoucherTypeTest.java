package org.programmers.springorder.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springorder.model.voucher.VoucherType;

import java.util.InputMismatchException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("VoucherType 종류 관련 테스트")
class VoucherTypeTest {


    @Test
    @DisplayName("유효하지 않은 바우처 타입을 입력하면 에러 메시지를 띄운다.")
    void invalidMenuNumber() {
        //given
        String menuNum = "4";

        //when
        //then

        assertThatThrownBy(() -> VoucherType.selectVoucherType(menuNum))
                .isInstanceOf(InputMismatchException.class)
                .hasMessage("유효하지 않은 값입니다. 다시 입력해주세요.");

    }

    @Test
    @DisplayName("유효한 바우처 타입을 입력하면 바우처 타입을 반환한다.")
    void validMenuNumber() {
        //given
        String menuNum = "1";
        String menuNum2 = "2";

        //when
        VoucherType voucherType = VoucherType.selectVoucherType(menuNum);
        VoucherType voucherType2 = VoucherType.selectVoucherType(menuNum2);

        //then
        assertThat(voucherType).isEqualTo(VoucherType.FIXED);
        assertThat(voucherType2).isEqualTo(VoucherType.PERCENT);
    }


    @Test
    @DisplayName("Fixed Amount 쿠폰을 사용해 할인받는다.")
    void useFixedAmountVoucher() {
        // given
        int beforeDiscount = 10000;
        int discountAmount = 1000;

        // when
        long result = VoucherType.FIXED.calculate(beforeDiscount, discountAmount);

        // then
        assertThat(result).isEqualTo(9000);
    }

    @Test
    @DisplayName("Percent Amount 쿠폰을 사용해 할인 받는다.")
    void usePercentDiscountVoucher() {
        // given
        int beforeDiscount = 10000;
        int discountAmount = 10;

        // when
        long result = VoucherType.PERCENT.calculate(beforeDiscount, discountAmount);

        // then
        assertThat(result).isEqualTo(9000);
    }

}