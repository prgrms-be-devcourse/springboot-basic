package org.programmers.springorder.voucher.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springorder.exception.ErrorCode;
import org.programmers.springorder.exception.VoucherException;
import org.programmers.springorder.voucher.model.VoucherType;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class VoucherRequestDtoTest {

    @Test
    @DisplayName("voucher 정보를 정상적으로 받아올 때 DTO 변환 테스트")
    void voucherRequestDtoSuccess() {
        long discountValue = 1000;
        VoucherType fixed = VoucherType.FIXED;

        long percentValue = 10;
        VoucherType percent = VoucherType.PERCENT;

        Assertions.assertThatCode(() -> new VoucherRequestDto(discountValue, fixed))
                .doesNotThrowAnyException();
        Assertions.assertThatCode(() -> new VoucherRequestDto(percentValue, percent))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("voucher discountValue를 정상적으로 받아오지 못할 때 오류 테스트")
    void voucherRequestDtoNotValidDiscountValue() {
        long discountValue = 100000;
        VoucherType fixed = VoucherType.FIXED;

        long percentValue = 500;
        VoucherType percent = VoucherType.PERCENT;

        Assertions.assertThatThrownBy(() -> new VoucherRequestDto(discountValue, fixed))
                .isInstanceOf(VoucherException.class)
                .hasMessage(ErrorCode.INVALID_DISCOUNT_VALUE.getMessage());
        Assertions.assertThatThrownBy(() -> new VoucherRequestDto(percentValue, percent))
                .isInstanceOf(VoucherException.class)
                .hasMessage(ErrorCode.INVALID_DISCOUNT_PERCENT.getMessage());
    }


}