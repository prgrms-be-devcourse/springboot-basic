package kr.co.programmers.springbootbasic.voucher.impl;

import kr.co.programmers.springbootbasic.exception.NoValidAmountException;
import kr.co.programmers.springbootbasic.voucher.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PercentAmountVoucherTest {
    @ParameterizedTest
    @ValueSource(longs = {0, -30, -50, -84, -99})
    @DisplayName("퍼센트할인이 0%보다 작거나 같으면 안된다.")
    void throwException_ifPercentAmountIs_zeroOrNegativePercent(long amount) {
        assertThrows(NoValidAmountException.class, () -> createPercentAmountVoucher(amount));
    }

    @ParameterizedTest
    @ValueSource(longs = {100, 101, 200, 500, Long.MAX_VALUE})
    @DisplayName("퍼센트할인이 100%보다 크거나 같으면 안된다.")
    void throwException_ifPercentAmountIs_oneHundredOrGreaterThanOneHundred(long amount) {
        assertThrows(NoValidAmountException.class, () -> createPercentAmountVoucher(amount));
    }

    @ParameterizedTest
    @ValueSource(longs = {5, 10, 33, 57, 80, 99})
    @DisplayName("퍼센트할인바우처 생성 성공 테스트")
    void createVoucherSuccess_ifProperPercentInput(long amount) {
        Voucher voucher = createPercentAmountVoucher(amount);
        assertThat(voucher.getAmount(), is(amount));
    }

    @ParameterizedTest
    @CsvSource({"1,0", "25,12", "30,15", "500, 250", "3232,1616", "7777,3888", "70000, 35000"})
    @DisplayName("percent discount 성공 테스트")
    void discountSuccessIfProperProductPriceInput(String input, String result) {
        // given
        long voucherAmount = 50;
        long productPrice = Long.parseLong(input);
        long discountedPrice = Long.parseLong(result);
        // when
        Voucher voucher = createPercentAmountVoucher(voucherAmount);
        // then
        assertThat(voucher.discount(productPrice), is(discountedPrice));
    }

    private Voucher createPercentAmountVoucher(long amount) {
        UUID uuid = UUID.randomUUID();
        return new PercentAmountVoucher(uuid, amount);
    }
}
