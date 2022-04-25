package com.waterfogsw.voucher.voucher;

import com.waterfogsw.voucher.global.MessageConverter;
import com.waterfogsw.voucher.global.VoucherTypeConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class MessageConverterTests {
    @Mock
    private VoucherTypeConverter voucherTypeConverter;

    @InjectMocks
    private MessageConverter converter;

    @Nested
    @DisplayName("convert 메서드는")
    class Describe_convert {

        @Nested
        @DisplayName("null Request 가 전달되면")
        class Context_with_null {
            String source = "iron_man";

            @Test
            @DisplayName("IllegalArgumentException 에러가 발생한다")
            void it_return_null() {
                assertThrows(IllegalArgumentException.class, () -> converter.convert(null));
            }
        }

        @Nested
        @DisplayName("존재하는 바우처 타입이 전달되면")
        class Context_with_existing_voucher_type {

            @ParameterizedTest
            @ValueSource(strings = {"FIXED_AMOUNT", "PERCENT_DISCOUNT"})
            @DisplayName("enum 바우처 타입을 반환한다")
            void it_return_enum(String source) {

            }
        }
    }
}
