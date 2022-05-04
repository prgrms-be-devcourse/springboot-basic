package org.programmers.springbootbasic.voucher.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("VoucherType enum")
class VoucherTypeTest {

    @Nested
    @DisplayName("findByNumber 메소드는")
    class findByTypeOf{

        @Nested
        @DisplayName("바우처 넘버가 유효하지 않은 값 일 때")
        class ContextWithUnValidVoucherNumber {

            @Test
            @DisplayName("예외를 던집니다.")
            void it_returns_a_throw() {
                assertThatThrownBy(() -> VoucherType.findByNumber(3))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("잘못된 바우처 넘버입니다.");
            }
        }
        @Nested
        @DisplayName("바우처 넘버가 유효한 값 일 때")
        class ContextWithValidVoucherNumber {
            VoucherType voucherType = VoucherType.findByNumber(2);

            @Test
            @DisplayName("바우처 타입을 반환합니다.")
            void it_has_a_voucherType() {
                assertThat(voucherType).isEqualTo(VoucherType.PERCENT);
            }

            @Test
            @DisplayName("바우처를 생성 할 수 있습니다.")
            void it_returns_a_voucher() {
                Voucher voucher = voucherType.create(UUID.randomUUID(), 50, LocalDateTime.now());

                assertThat(voucher.getVoucherType()).isEqualTo(voucherType);
            }
        }
    }

    @Nested
    @DisplayName("findByNumber 메소드는")
    class findByNumberOf {

        @Nested
        @DisplayName("바우처 타입이 유효하지 않은 값 일 때")
        class ContextWithUnValidVoucherType {

            @Test
            @DisplayName("예외를 던집니다.")
            void it_returns_a_throw() {
                assertThatThrownBy(() -> VoucherType.findByType("unknown"))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("잘못된 바우처 타입입니다.");
            }
        }
        @Nested
        @DisplayName("바우처 타입이 유효한 값 일 때")
        class Context_with_valid_voucherType {
            VoucherType voucherType = VoucherType.findByType("FIXED");

            @Test
            @DisplayName("바우처 타입을 반환합니다.")
            void it_has_a_voucherType() {
                assertThat(voucherType).isEqualTo(VoucherType.FIXED);
            }

            @Test
            @DisplayName("바우처를 생성 할 수 있습니다.")
            void it_returns_a_voucher() {
                Voucher voucher = voucherType.create(UUID.randomUUID(), 50, LocalDateTime.now());

                assertThat(voucher.getVoucherType()).isEqualTo(voucherType);
            }
        }
    }
}