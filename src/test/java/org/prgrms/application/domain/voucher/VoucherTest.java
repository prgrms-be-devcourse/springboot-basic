package org.prgrms.application.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.application.domain.voucher.typepolicy.VoucherTypePolicy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.prgrms.application.domain.voucher.VoucherType.FIXED;
import static org.prgrms.application.domain.voucher.VoucherType.PERCENT;

class VoucherTest {

    Long id = 1L;
    /**
     * 고정금액 할인 바우처 테스트
     */

    @ParameterizedTest
    @DisplayName("고정 금액 할인 바우처 생성이 성공한다.")
    @ValueSource(doubles = {10000})
    void fixedVoucherCreationSuccess(double discountAmount) {
        Voucher voucher = new Voucher(id, FIXED.applyPolicy(discountAmount));
        VoucherTypePolicy voucherTypePolicy = voucher.getVoucherTypePolicy();
        assertThat(voucherTypePolicy.equals(FIXED));
    }

    @DisplayName("고정 금액 할인 바우처는 할인 금액이 고정 금액보다 높으면 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(doubles = {10000})
    void fixedDiscountExceedsAmount(double discountAmount) {
        Voucher voucher = new Voucher(id, FIXED.applyPolicy(discountAmount));
        VoucherTypePolicy fixedPolicy = voucher.getVoucherTypePolicy();

        double stockPrice = 100;

        assertThatThrownBy(() -> fixedPolicy.discount(stockPrice)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("고정 금액 할인이 음수이면 예외가 발생한다.")
    @ValueSource(doubles = {-10000,-1})
    void fixedVoucherNegativeFailTest(double discountAmount) {

        assertThatThrownBy(() -> new Voucher(id,FIXED.applyPolicy(discountAmount))).isInstanceOf(IllegalArgumentException.class);
    }


    @ParameterizedTest
    @DisplayName("고정 금액 할인 바우처가 정상이면 할인에 성공한다.")
    @ValueSource(doubles = {10000,19999})
    void fixedAmountVoucherSuccessTest(double discountAmount) {
        Voucher voucher = new Voucher(id,FIXED.applyPolicy(discountAmount));
        VoucherTypePolicy fixedPolicy = voucher.getVoucherTypePolicy();
        double stockPrice = 20000;
        double result = fixedPolicy.discount(stockPrice);

        assertThat(result).isEqualTo(stockPrice-discountAmount);
    }


    /**
     * 퍼센트 할인 바우처 테스트
     */

    @ParameterizedTest
    @DisplayName("퍼센트 금액 할인 바우처 생성이 성공한다.")
    @ValueSource(doubles = {1,99,50})
    void percentVoucherCreationSuccess(double discountAmount) {
        Voucher voucher = new Voucher(id, PERCENT.applyPolicy(discountAmount));
        VoucherTypePolicy voucherTypePolicy = voucher.getVoucherTypePolicy();
        assertThat(voucherTypePolicy.equals(PERCENT));
    }

    @DisplayName("퍼센트 할인 바우처는 100이상 0이하면 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(doubles = {100, -1,0})
    void percentDiscountFailTest(double percent) {
        assertThatThrownBy(() -> new Voucher(id, PERCENT.applyPolicy(percent))).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("퍼센트 금액 할인이 성공한다.")
    @ValueSource(doubles = {1,99,50})
    void percentVoucherDiscountSuccess(double percent) {
        Voucher voucher = new Voucher(id,  PERCENT.applyPolicy(percent));
        VoucherTypePolicy percentPolicy = voucher.getVoucherTypePolicy();

        double stockPrice = 20000;
        double result = percentPolicy.discount(stockPrice);

        assertThat(result).isEqualTo(stockPrice*percent/100);
    }

}