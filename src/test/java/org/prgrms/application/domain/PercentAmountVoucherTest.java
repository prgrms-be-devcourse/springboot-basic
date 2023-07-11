//package org.prgrms.application.domain;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//import org.junit.jupiter.params.provider.ValueSource;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//class PercentAmountVoucherTest {
//    Long id = 1L;
//
//    @DisplayName("퍼센트 할인 바우처는 지정된 퍼센트 범위를 벗어나면 예외를 발생한다.")
//    @ParameterizedTest
//    @ValueSource(doubles = {100, -1})
//    void percentDiscountFailTest(double percent) {
//
//        assertThatThrownBy(() -> new PercentAmountVoucher(id, percent))
//                .isInstanceOf(IllegalArgumentException.class);
//    }
//
//    @DisplayName("퍼센트 할인 바우처가 지정된 퍼센트 범위이면 통과한다.")
//    @ParameterizedTest
//    @CsvSource({"50","99","1","24"})
//    void percentDiscountSuccessTest(double percent) {
//
//        PercentAmountVoucher voucher = new PercentAmountVoucher(id, percent);
//        double stockPrice = 10000;
//        double result = voucher.discount(stockPrice);
//
//        assertThat(result).isEqualTo(5000);
//    }
//
//
//}