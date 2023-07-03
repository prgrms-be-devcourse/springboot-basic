package kr.co.programmers.springbootbasic.voucher.domain.impl;

import kr.co.programmers.springbootbasic.voucher.exception.NoValidAmountException;
import kr.co.programmers.springbootbasic.voucher.domain.Voucher;
import kr.co.programmers.springbootbasic.voucher.domain.impl.FixedAmountVoucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FixedAmountVoucherTest {
    @ParameterizedTest
    @ValueSource(longs = {0, -1000, -2000, -3234, Long.MIN_VALUE})
    @DisplayName("고정할인가격이 0보다 작거나 같으면 안된다.")
    void throwExceptionIfFixedAmountIsNotPositive(long amount) {
        assertThrows(NoValidAmountException.class, () -> createFixedAmountVoucher(amount));
    }

    @ParameterizedTest
    @ValueSource(longs = {1_000_001, 2_000_000, 999_999_999, Long.MAX_VALUE})
    @DisplayName("고정할인가격이 100만원보다 크면 안된다.")
    void throwExceptionIfFixedAmountIsMoreThanOneHundredWon(long amount) {
        assertThrows(NoValidAmountException.class, () -> createFixedAmountVoucher(amount));
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 80, 100, 1000, 50_000, 200_000, 999_000})
    @DisplayName("고정할인바우처 생성 성공 테스트")
    void createVoucherSuccessIfProperAmountInput(long amount) {
        Voucher voucher = createFixedAmountVoucher(amount);
        assertThat(voucher.getAmount(), is(amount));
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 100, 300, 1500, 3000, 10_000, 333_333, 999_999})
    @DisplayName("할인가격이 상품가격보다 크면 예외가 발생한다.")
    void throwExceptionIfProductPriceLessThanVoucherAmount(long productPrice) {
        // given, when
        Voucher voucher = createFixedAmountVoucher(1_000_000);
        // then
        assertThrows(NoValidAmountException.class, () -> voucher.discount(productPrice));
    }

    @ParameterizedTest
    @CsvSource({"50000,20000", "70000, 40000", "30000,0"})
    @DisplayName("discount 성공 테스트")
    void discountSuccessIfProperProductPriceInput(String input, String result) {
        // given
        long voucherAmount = 30_000;
        long productPrice = Long.parseLong(input);
        long discountedPrice = Long.parseLong(result);
        // when
        Voucher voucher = createFixedAmountVoucher(voucherAmount);
        // then
        assertThat(voucher.discount(productPrice), is(discountedPrice));
    }

    private Voucher createFixedAmountVoucher(long amount) {
        UUID uuid = UUID.randomUUID();
        return new FixedAmountVoucher(uuid, amount);
    }
}
