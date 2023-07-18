package com.prgrms.springbootbasic.domain.voucher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.prgrms.springbootbasic.enums.voucher.VoucherType;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RateVoucherTest {

    //해피 케이스 테스트 - Hamcrest 테스트
    @Test
    @DisplayName("정률 할인(Rate) 바우처 생성 테스트")
    void createRateVoucherTest() {
        //given
        UUID voucherId = UUID.randomUUID();
        long discount = 70;
        LocalDateTime createdAt = LocalDateTime.now();

        //when
        RateVoucher voucher = new RateVoucher(voucherId, discount, createdAt);

        //then
        assertThat(voucher.getVoucherId(), is(equalTo(voucherId)));
        assertThat(voucher.getDiscount(), Matchers.equalTo(discount));
        assertThat(voucher.getVoucherType(), Matchers.equalTo(VoucherType.RATE));
        assertThat(voucher.getCreatedAt(), is(equalTo(createdAt)));
    }

    //엣지 케이스 테스트 - Nested 클래스 사용
    // 1~99이외의 퍼센트가 들어왔을 때의 테스트, 1~99범위의 테스트가 들와왔을 때 테스트
    @Nested
    @DisplayName("정률 할인(Rate) 바우처의 Validation")
    class checkRateValidationTest {
        //given

        @Test
        @DisplayName("퍼센트가 1~99 이외 범위일 경우")
        void invalidDiscountTest() {
            //given
            long discount = 100;

            //then
            assertThrows(IllegalArgumentException.class, () -> new RateVoucher(discount));

        }


        @Test
        @DisplayName("퍼센트가 1~99 사이의 범위일 경우")
        void validDiscountTest() {
            //given
            long discount = 50;

            //when
            RateVoucher voucher = new RateVoucher(discount);

            //then
            assertThat(voucher.getDiscount(), Matchers.equalTo(discount));
        }
    }

    //해피케이스 테스트 -Hamcreset 테스트

    @DisplayName("여러 개의 정률 할인(Rate) 바우처 생성 테스트")
    @ParameterizedTest
    @CsvSource(value = {"1", "50", "99"})
    void createMultiRateVoucherTest(long discount) {
        //given
        UUID voucherId = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();
        //then
        RateVoucher voucher = new RateVoucher(voucherId, discount, createdAt);

        //when
        assertThat(voucher.getVoucherId(), is(equalTo(voucherId)));
        assertThat(voucher.getDiscount(), Matchers.equalTo(discount));
        assertThat(voucher.getVoucherType(), Matchers.equalTo(VoucherType.RATE));
        assertThat(voucher.getCreatedAt(), is(equalTo(createdAt)));
    }
}