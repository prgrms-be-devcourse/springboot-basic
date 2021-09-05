package com.programmers.kdtspringorder.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class PercentDiscountVoucherTest {

    @ParameterizedTest
    @ValueSource(longs = {20, 1, 50})
    @DisplayName("할인율 정상 테스트")
    public void discount(@ConvertWith(PercentVoucherConverter.class) PercentDiscountVoucher percentDiscountVoucher) {
        //when
        long discount = percentDiscountVoucher.discount(100L);

        //then
        assertThat(discount).isEqualTo(100L - 100L * percentDiscountVoucher.getDiscountValue() / 100L);
    }


    static class PercentVoucherConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object o, Class<?> aClass) throws ArgumentConversionException {
            return new PercentDiscountVoucher(UUID.randomUUID(), (long) o);
        }
    }

    @ParameterizedTest(name = "{index} {displayName} percent = {0}")
    @ValueSource(ints = {-10, -1, 51, 100})
    @DisplayName("할인율은 0 초과 50 이하입니다.")
    public void parameterizedDiscountRateTest(Integer percent) {
        assertThatIllegalArgumentException().isThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(), percent));
    }

    @Test
    @DisplayName("할인율은 0초과 50 이하 입니다.")
    public void discountRateTest() {
        assertThatIllegalArgumentException().isThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(), -1));
        assertThatIllegalArgumentException().isThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(), 0));
        assertThatIllegalArgumentException().isThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(), 51));
    }
}