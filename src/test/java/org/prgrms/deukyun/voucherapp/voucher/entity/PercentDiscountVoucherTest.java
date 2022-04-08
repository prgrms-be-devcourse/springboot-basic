package org.prgrms.deukyun.voucherapp.voucher.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * PDV - abbreviation of PercentDiscountVoucher
 */
class PercentDiscountVoucherTest {

    @Test
    void givenPercent20_whenConstructPDV_thenIsCreated() {
        //setup
        long percent = 20L;

        //when
        Voucher voucher = new PercentDiscountVoucher(percent);

        //assert
        assertThat(voucher).isNotNull();
    }

    @Test
    void givenNegativePercent_whenConstructPDV_thenThrowIllegalArgumentException() {
        //setup
        long amount = -1000L;

        //assert throws
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new PercentDiscountVoucher(amount));

    }

    @Test
    void givenAnyBeforeDiscountPrice_whenDiscount_thenReturnPercentDiscountedPrice() {
        //setup
        Voucher voucher = new PercentDiscountVoucher(20L);
        long beforeDiscountPrice = 1000L;

        //when
        long discountedPrice = voucher.discount(beforeDiscountPrice);

        //assert
        assertThat(discountedPrice).isEqualTo(800L);
    }
}