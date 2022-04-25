package com.waterfogsw.voucher.voucher;

import com.waterfogsw.voucher.global.VoucherTypeConverter;
import com.waterfogsw.voucher.voucher.domain.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VoucherTypeConverterTests {

    private final VoucherTypeConverter converter = new VoucherTypeConverter();

    @Nested
    @DisplayName("convert 메서드는")
    class Describe_convert {

        @Nested
        @DisplayName("존재하지 않는 바우처 타입이 전달되면")
        class Context_with_not_existing_voucher_type {
            String source = "iron_man";

            @Test
            @DisplayName("IllegalArgumentException 에러가 발생한다")
            void it_return_null() {
                assertThrows(IllegalArgumentException.class, () -> converter.convert(source));
            }
        }

        @Nested
        @DisplayName("존재하는 바우처 타입이 전달되면")
        class Context_with_existing_voucher_type {

            @ParameterizedTest
            @ValueSource(strings = {"FIXED_AMOUNT", "PERCENT_DISCOUNT"})
            @DisplayName("enum 바우처 타입을 반환한다")
            void it_return_enum(String source) {
                var type = converter.convert(source);
                assertThat(type, instanceOf(VoucherType.class));
            }
        }
    }
}
