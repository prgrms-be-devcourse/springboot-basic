package com.dev.bootbasic.voucher.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

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

        Assertions.assertThat(voucher)
                .extracting(Voucher::getVoucherType, Voucher::getDiscountAmount)
                .containsExactly(expectedVoucherType, amount);
    }

}
