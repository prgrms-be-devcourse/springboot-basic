package org.prgrms.deukyun.voucherapp.domain.voucher.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * FDV - abbreviation of FixedAmountDiscountVoucher
 */
class FixedAmountDiscountVoucherTest {

    @Test
    void givenAmount1000_whenConstructFDV_thenIsCreated() {
        //setup
        long amount = 1000L;

        //when
        Voucher voucher = fixedAmountDiscountVoucherWithAmount(amount);

        //assert
        assertThat(voucher).isNotNull();
    }

    @Test
    void givenNegativeAmount_whenConstructFDV_thenThrowIllegalArgumentException() {
        //setup
        long amount = -1000L;

        //assert throws
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new FixedAmountDiscountVoucher(amount));
    }

    @Test
    void givenBeforeDiscountPriceGreaterThanAmountOfFDV_whenDiscount_thenThrowsIllegalArgumentException() {
        //setup
        Voucher voucher = fixedAmountDiscountVoucherWithAmount(1000L);
        long beforeDiscountPrice = 500L;

        //assert throws
        assertThatIllegalArgumentException()
                .isThrownBy(() -> voucher.discount(beforeDiscountPrice));
    }

    @Test
    void givenBeforeDiscountPriceSmallerThanAmountOfFDV_whenDiscount_thenReturnFixedAmountDiscountedPrice() {
        //setup
        long amount = 500L;
        Voucher voucher = fixedAmountDiscountVoucherWithAmount(amount);
        long beforeDiscountPrice = 1000L;

        //when
        long discountedPrice = voucher.discount(beforeDiscountPrice);

        //assert
        assertThat(discountedPrice).isEqualTo(beforeDiscountPrice - amount);
    }

    private FixedAmountDiscountVoucher fixedAmountDiscountVoucherWithAmount(long amount) {
        return new FixedAmountDiscountVoucher(amount);
    }
}