//package org.prgrms.application.domain;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.ValueSource;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
//
//class FixedAmountVoucherTest {
//
//    Long id = 1L;
//
//    @ParameterizedTest
//    @DisplayName("고정 금액 할인 금액이 상품 금액보다 높으면 실패한다.")
//    @ValueSource(doubles = {10000})
//    void fixedAmountVoucherFailTest(double discountAmount) {
//
//        FixedAmountVoucher voucher = new FixedAmountVoucher(id, discountAmount);
//        double stockPrice = 100;
//
//        assertThatThrownBy(()->voucher.discount(stockPrice)).isInstanceOf(IllegalArgumentException.class);
//    }
//
//    @ParameterizedTest
//    @DisplayName("고정 금액 할인이 음수이면 예외가 발생한다.")
//    @ValueSource(doubles = {-10000})
//    void fixedAmountVoucherNegativeFailTest(double discountAmount) {
//
//        assertThatThrownBy(() -> new FixedAmountVoucher(id, discountAmount))
//                .isInstanceOf(IllegalArgumentException.class);
//    }
//
//    @ParameterizedTest
//    @DisplayName("고정 금액 할인 바우처가 정상이면 할인에 성공한다.")
//    @ValueSource(doubles = {10000})
//    void fixedAmountVoucherSuccessTest(double discountAmount) {
//
//        FixedAmountVoucher voucher = new FixedAmountVoucher(id, discountAmount);
//        double stockPrice = 20000;
//        double result = voucher.discount(stockPrice);
//
//        assertThat(result).isEqualTo(10000);
//    }
//}