package com.example.voucherproject.voucher;

import com.example.voucherproject.common.Helper;
import com.example.voucherproject.voucher.model.Voucher;
import com.example.voucherproject.voucher.model.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static com.example.voucherproject.common.Helper.VoucherHelper.makeVoucher;
import static com.example.voucherproject.voucher.model.VoucherType.FIXED;
import static com.example.voucherproject.voucher.model.VoucherType.PERCENT;
import static org.assertj.core.api.Assertions.assertThat;

class VoucherTest {

    @Nested
    @DisplayName("바우처 생성 테스트")
    class VoucherFactoryTest {

        @ParameterizedTest
        @EnumSource(value = VoucherType.class, names= {"FIXED", "PERCENT"})
        @DisplayName("[create:성공] 각 타입의 바우처를 생성할 수 있다.")
        void createFixedTypeVoucherTest(VoucherType voucherType){
            var voucher = makeVoucher(voucherType,1000L);
            assertThat(voucher).isInstanceOf(Voucher.class);
            assertThat(voucher.getType()).isEqualTo(voucherType);
            assertThat(voucher.getAmount()).isEqualTo(1000L);
        }
    }

    @Nested
    @DisplayName("바우처 할인 테스트")
    class VoucherDiscountTest {

        @Test
        @DisplayName("[discount:성공] FIXED 타입 바우처 할인 후 금액 검증")
        void fixedTypeDiscountTest() {
            var voucher = makeVoucher(FIXED, 1000L);
            Long discountedPrice = voucher.discount(10000L);
            assertThat(discountedPrice).isEqualTo(9000L);
        }

        @Test
        @DisplayName("[discount:성공] PERCENT 타입 바우처 할인 후 금액 검증")
        void percentTypeDiscountTest() {
            var voucher =  makeVoucher(PERCENT, 25L);
            Long discountedPrice = voucher.discount(10000L);
            assertThat(discountedPrice).isEqualTo(7500L);
        }
    }

}