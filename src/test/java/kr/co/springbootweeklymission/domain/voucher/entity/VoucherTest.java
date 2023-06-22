package kr.co.springbootweeklymission.domain.voucher.entity;

import kr.co.springbootweeklymission.domain.model.VoucherPolicy;
import kr.co.springbootweeklymission.domain.voucher.exception.WrongVoucherPolicyException;
import kr.co.springbootweeklymission.global.error.ResponseStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class VoucherTest {

    @Test
    @DisplayName("고정_할인_가격이_할인전_가격보다_큰_경우 - WrongVoucherPolicyException")
    void 고정_할인_가격이_할인전_가격보다_큰_경우_Exception() {
        //given
        Voucher voucher = Voucher.builder()
                .amount(100)
                .voucherPolicy(VoucherPolicy.FIXED_DISCOUNT)
                .build();

        //when & then
        assertThatThrownBy(() -> voucher.discount(10))
                .isInstanceOf(WrongVoucherPolicyException.class)
                .hasMessage(ResponseStatus.FAIL_WRONG_DISCOUNT.getMessage());
    }

    @Test
    @DisplayName("퍼센트_할인이_할인전_가격보다_큰_경우 - WrongVoucherPolicyException")
    void 퍼센트_할인이_할인전_가격보다_큰_경우_Exception() {
        //given
        Voucher voucher = Voucher.builder()
                .amount(100)
                .voucherPolicy(VoucherPolicy.FIXED_DISCOUNT)
                .build();

        //when & then
        assertThatThrownBy(() -> voucher.discount(10))
                .isInstanceOf(WrongVoucherPolicyException.class)
                .hasMessage(ResponseStatus.FAIL_WRONG_DISCOUNT.getMessage());
    }

    @Test
    @DisplayName("성공적으로_고정_할인을_진행한다 - SUCCESS")
    void 성공적으로_고정_할인을_진행한다() {
        //given
        Voucher voucher = Voucher.builder()
                .amount(100)
                .voucherPolicy(VoucherPolicy.FIXED_DISCOUNT)
                .build();

        //when
        int actual = voucher.discount(200);

        //then
        assertThat(actual).isEqualTo(100);
    }

    @Test
    @DisplayName("성공적으로_퍼센트_할인을_진행한다 - SUCCESS")
    void 성공적으로_퍼센트_할인을_진행한다() {
        //given
        Voucher voucher = Voucher.builder()
                .amount(10)
                .voucherPolicy(VoucherPolicy.PERCENT_DISCOUNT)
                .build();

        //when
        int actual = voucher.discount(100);

        //then
        assertThat(actual).isEqualTo(90);
    }
}
