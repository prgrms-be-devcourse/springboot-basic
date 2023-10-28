package com.programmers.springbootbasic.domain.voucher.presentation.dto;

import static com.programmers.springbootbasic.exception.ErrorCode.INVALID_VOUCHER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.programmers.springbootbasic.exception.exceptionClass.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateVoucherRequestTest {

    @DisplayName("of 메서드를 통해 적절한 CreateVoucherRequest 객체를 반환한다.")
    @Test
    void success_of() {
        // given
        String stringVoucherType = "FIXED";
        Integer benefitValue = 100;

        // when
        CreateVoucherRequest request = CreateVoucherRequest.of(stringVoucherType, benefitValue);

        // then
        assertThat(request.getVoucherType()).isNotNull();
        assertThat(request.getBenefitValue()).isEqualTo(benefitValue);
    }

    @DisplayName("올바르지 않은 VoucherType 문자열로 of 메서드를 호출할 경우 예외를 발생시킨다.")
    @Test
    void fail_of() {
        // given
        String invalidStringVoucherType = "INVALID_TYPE";
        Integer benefitValue = 100;

        // then
        assertThatThrownBy(() -> CreateVoucherRequest.of(invalidStringVoucherType, benefitValue))
            .isInstanceOf(CustomException.class)
            .hasMessageContaining(INVALID_VOUCHER.getMessage());
    }
}
