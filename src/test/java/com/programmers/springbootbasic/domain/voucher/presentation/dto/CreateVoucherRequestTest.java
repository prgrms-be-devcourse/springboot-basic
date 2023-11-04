package com.programmers.springbootbasic.domain.voucher.presentation.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherTypeEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateVoucherRequestTest {

    @DisplayName("of 메서드를 통해 적절한 CreateVoucherRequest 객체를 반환한다.")
    @Test
    void success_of() {
        // given
        VoucherTypeEnum voucherType = VoucherTypeEnum.FIXED;
        Integer benefitValue = 100;

        // when
        CreateVoucherRequest request = CreateVoucherRequest.of(voucherType, benefitValue);

        // then
        assertThat(request.getVoucherType()).isNotNull();
        assertThat(request.getBenefitValue()).isEqualTo(benefitValue);
    }
}
