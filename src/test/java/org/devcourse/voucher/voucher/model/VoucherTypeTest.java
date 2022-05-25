package org.devcourse.voucher.voucher.model;

import org.devcourse.voucher.application.voucher.model.FixedAmountVoucher;
import org.devcourse.voucher.application.voucher.model.PercentDiscountVoucher;
import org.devcourse.voucher.application.voucher.model.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.devcourse.voucher.application.voucher.model.VoucherType.FIXED_AMOUNT_VOUCHER;
import static org.devcourse.voucher.application.voucher.model.VoucherType.PERCENT_DISCOUNT_VOUCHER;

class VoucherTypeTest {

    @Test
    @DisplayName("옵션을 기준으로 원하는 타입이 생성되는지 테스트")
    void findOptionVoucherTest() {
        String[] stubs = {
                "1", "2"
        };
        VoucherType[] want = {
                FIXED_AMOUNT_VOUCHER, PERCENT_DISCOUNT_VOUCHER
        };

        for (int i = 0; i < stubs.length; i++) {
            assertThat(VoucherType.optionDiscriminate(stubs[i])).isEqualTo(want[i]);
        }
    }

    @Test
    @DisplayName("이름을 기준으로 원하는 타입이 생성되는지 테스트")
    void findNameVoucherTest() {
        String[] stubs = {
                "FixedAmountVoucher", "PercentDiscountVoucher"
        };
        VoucherType[] want = {
                FIXED_AMOUNT_VOUCHER, PERCENT_DISCOUNT_VOUCHER
        };

        for (int i = 0; i < stubs.length; i++) {
            assertThat(VoucherType.nameDiscriminate(stubs[i])).isEqualTo(want[i]);
        }
    }

    @Test
    @DisplayName("특정 바우처 타입이 주어졌을때 적절한 바우처 타입이 생성되는지 테스트")
    void voucherCreateTest() {
        VoucherType[] stubs = {
            FIXED_AMOUNT_VOUCHER, PERCENT_DISCOUNT_VOUCHER
        };

        Class<?>[] want = {
                FixedAmountVoucher.class, PercentDiscountVoucher.class
        };

        for (int i = 0; i < stubs.length; i++) {
            assertThat(stubs[i].voucherCreator(UUID.randomUUID(), 10)).isInstanceOf(want[i]);
        }
    }
}