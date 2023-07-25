package org.programmers.VoucherManagement.voucher.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.programmers.VoucherManagement.voucher.exception.VoucherException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DiscountTypeTest {
    @Test
    @DisplayName("입력값에 따라 DiscountType을 반환할 수 있다. - 성공")
    void from_InputTypeString_EqualsDiscountType() {
        //given
        String fixedInput = "fixed";
        String percentInput = "percent";

        //when
        DiscountType fixedTypeExpect = DiscountType.from(fixedInput);
        DiscountType percentTypeExpect = DiscountType.from(percentInput);

        //then
        assertThat(fixedTypeExpect).isEqualTo(DiscountType.FIXED);
        assertThat(percentTypeExpect).isEqualTo(DiscountType.PERCENT);
    }

    @ParameterizedTest
    @DisplayName("옳지 않은 입력값이 들어오면 DiscountType으로 변환할 수 없다. - 실패")
    @CsvSource({"fisss", "fercent", "percen"})
    void from_InputTypeString_ThrowVoucherException(String inputType) {

        //when&then
        assertThatThrownBy(() -> DiscountType.from(inputType))
                .isInstanceOf(VoucherException.class)
                .hasMessage("해당하는 유형의 바우처가 존재하지 않습니다.");
    }

    @ParameterizedTest
    @DisplayName("DiscountType의 모든 값을 테스트한다. - 성공")
    @EnumSource(DiscountType.class)
    void test_MenuTypeNotNull_Success(DiscountType discountType) {
        Assertions.assertThat(discountType).isNotNull();
    }
}
