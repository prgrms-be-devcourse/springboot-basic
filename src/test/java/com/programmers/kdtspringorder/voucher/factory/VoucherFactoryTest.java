package com.programmers.kdtspringorder.voucher.factory;

import com.programmers.kdtspringorder.voucher.VoucherType;
import com.programmers.kdtspringorder.voucher.domain.FixedAmountVoucher;
import com.programmers.kdtspringorder.voucher.domain.PercentDiscountVoucher;
import com.programmers.kdtspringorder.voucher.domain.Voucher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class VoucherFactoryTest {

    @ParameterizedTest
    @MethodSource("voucherType")
    @DisplayName("타입에 맞는 바우처를 생성해야 한다.")
    void createVoucher(VoucherType type, Class<Voucher> voucherClass) {
        // Given
        VoucherFactory voucherFactory = new VoucherFactory();

        // When
        Voucher voucher = voucherFactory.createVoucher(type, 10L);

        // Then
        assertThat(voucher).isInstanceOf(voucherClass);
    }

    private static Stream<Arguments> voucherType() {
        return Stream.of(
                Arguments.of(VoucherType.FIXED, FixedAmountVoucher.class),
                Arguments.of(VoucherType.PERCENT, PercentDiscountVoucher.class)
        );
    }

}