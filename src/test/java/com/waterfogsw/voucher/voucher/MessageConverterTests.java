package com.waterfogsw.voucher.voucher;

import com.waterfogsw.voucher.console.Command;
import com.waterfogsw.voucher.global.MessageConverter;
import com.waterfogsw.voucher.global.PostRequest;
import com.waterfogsw.voucher.global.VoucherTypeConverter;
import com.waterfogsw.voucher.voucher.domain.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

            @Test
            @DisplayName("IllegalArgumentException 에러가 발생한다")
            void it_return_null() {
                assertThrows(IllegalArgumentException.class, () -> converter.convert(null));
            }
        }

        @Nested
        @DisplayName("유효한 Command 의 Request 가 전달되면")
        class Context_with_existing_voucher_type {

            @ParameterizedTest
            @EnumSource(Command.class)
            @DisplayName("DTO 를 생성한다")
            void it_return_enum(Command command) {
                var postRequest = new PostRequest(command, "FIXED_AMOUNT", "1000");

                when(voucherTypeConverter.convert(any())).thenReturn(VoucherType.FIXED_AMOUNT);

                var voucherDto = converter.convert(postRequest);

                assertThat(voucherDto.type(), is(VoucherType.FIXED_AMOUNT));
            }
        }
    }
}
