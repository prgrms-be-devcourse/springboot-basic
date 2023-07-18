package com.prgrms.springbootbasic.domain.voucher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.prgrms.springbootbasic.enums.voucher.VoucherType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class FixedVoucherTest {

    //해피케이스 테스트 - Hamcrest 테스트
    @Test
    @DisplayName("고정 할인(Fixed) 바우처 생성 테스트")
    void createFixedVoucherTest() {
        //given
        long discount = 10000;

        //when
        FixedVoucher voucher = new FixedVoucher(discount);

        //then
        assertThat(voucher.getVoucherId(), Matchers.notNullValue());
        assertThat(voucher.getDiscount(), Matchers.equalTo(discount));
        assertThat(voucher.getVoucherType(), Matchers.equalTo(VoucherType.FIXED));
        assertThat(voucher.getCreatedAt(), Matchers.notNullValue());
    }

    //엣지 케이스 테스트 - Nested클래스
    // 사용 0이하의 금액이 들어왔을 때 테스트, 0초과의 금액이 들어왔을 때의 테스트

    @Nested
    @DisplayName("고정 할인(Fixed) 바우처의 Validation 확인 테스트")
    class checkFixedValidationTest {

        @Test
        @DisplayName("금액이 0 이하일 경우")
        void invalidDiscountTest() {
            //given
            long discount = -1000;

            //then
            assertThrows(IllegalArgumentException.class, () -> new FixedVoucher(discount));
        }

        @Test
        @DisplayName("금액이 0 초과일 경우")
        void validDiscountTest() {
            //given
            long discount = 2000;

            //when
            FixedVoucher voucher = new FixedVoucher(discount);

            //then
            assertThat(voucher.getDiscount(), Matchers.equalTo(discount));
        }
    }


    //해피케이스 테스트 - Hamcrest 테스트
    @DisplayName("여러 개의 고정 할인(fixed) 바우처 생성 테스트")
    @ParameterizedTest
    @CsvSource(value = {"1000", "5000", "10000"})
    void createMultiFixcedVoucherTest(long discount) {
        //given

        //when
        FixedVoucher voucher = new FixedVoucher(discount);

        //then
        assertThat(voucher.getVoucherId(), Matchers.notNullValue());
        assertThat(voucher.getDiscount(), Matchers.equalTo(discount));
        assertThat(voucher.getVoucherType(), Matchers.equalTo(VoucherType.FIXED));
        assertThat(voucher.getCreatedAt(), Matchers.notNullValue());
    }
}