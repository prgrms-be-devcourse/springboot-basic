package com.dev.bootbasic.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoucherFactoryTest {

    VoucherFactory voucherFactory = new VoucherFactory();

    @DisplayName("요청에 맞는 바우처 타입을 찾아 바우처를 생성합니다.")
    @ParameterizedTest
    @CsvSource({
            "fixed, 5000, FIXED",
            "percent, 50, PERCENT"
    })
    void createVoucherTest(String type, int amount, VoucherType expectedVoucherType) {
        Voucher voucher = voucherFactory.create(UUID.randomUUID(), type, amount);

        assertThat(voucher)
                .extracting(Voucher::getVoucherType, Voucher::getDiscountAmount)
                .containsExactly(expectedVoucherType, amount);
    }

    @DisplayName("바우처를 생성하지 못하면 예외를 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "asd, 5000, NoSuchElementException",
            ", 50, NoSuchElementException",
    })
    void createVoucherFailTest(String type, int amount, RuntimeException exception) {

        assertThatThrownBy(() -> voucherFactory.create(UUID.randomUUID(), type, amount))
                .isInstanceOf(exception.getClass());
    }

}
