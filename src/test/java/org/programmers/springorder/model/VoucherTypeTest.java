package org.programmers.springorder.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.programmers.springorder.model.voucher.VoucherType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoucherTypeTest {

    @ParameterizedTest
    @CsvSource(value = {"1, FIXED", "2, PERCENT"})
    @DisplayName("유효한 바우처 타입을 입력하면 바우처 타입을 반환한다.")
    void selectVoucherType(String menuNum, VoucherType expectedVoucherType) {
        // when
        VoucherType result = VoucherType.selectVoucherType(menuNum);

        // then
        assertThat(result).isEqualTo(expectedVoucherType);
    }

    @ParameterizedTest
    @CsvSource(value = {"3", "o"})
    @DisplayName("유효하지 않은 바우처 타입을 입력하면, 에러 메시지를 띄운다.")
    void selectVoucherTypeFail(String menuNum) {
        assertThatThrownBy(() -> VoucherType.selectVoucherType(menuNum))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 값입니다. 다시 입력해주세요.");

    }


    @ParameterizedTest
    @CsvSource(value = {"FIXED, 10000, 1000, 9000", "PERCENT, 10000, 10, 9000"})
    @DisplayName("쿠폰을 사용해 할인받는다.")
    void calculate(VoucherType voucherType, long beforeDiscount, long discountValue, long expectedResult) {
        // when
        long result = voucherType.calculate(beforeDiscount, discountValue);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(value = {"FIXED, 10", "PERCENT, 100"})
    @DisplayName("할인 금액 또는 할인율이 유효하지 않다면, 에러 메시지를 띄운다.")
    void validateDiscountRangeFail(VoucherType voucherType, long discountValue) {
        assertThatThrownBy(() -> VoucherType.validateDiscountRange(voucherType, discountValue))
                .isInstanceOf(IllegalArgumentException.class);
    }

}