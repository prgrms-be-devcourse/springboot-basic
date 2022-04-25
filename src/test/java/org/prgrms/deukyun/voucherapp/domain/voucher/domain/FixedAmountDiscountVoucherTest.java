package org.prgrms.deukyun.voucherapp.domain.voucher.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * FDV - abbreviation of FixedAmountDiscountVoucher
 */
class FixedAmountDiscountVoucherTest {

    @Test
    void 생성_성공() {
        //given
        long amount = 1000L;

        //when
        Voucher voucher = fixedAmountDiscountVoucherWithAmount(amount);

        //then
        assertThat(voucher).isNotNull();
    }

    @Test
    void 생성_실패_음수_amount() {
        //setup
        long amount = -1000L;

        //assert throws
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new FixedAmountDiscountVoucher(amount));
    }

    @Test
    void 생성_실패_정가_가_할인액_초과() {
        //setup
        Voucher voucher = fixedAmountDiscountVoucherWithAmount(1000L);
        long beforeDiscountPrice = 500L;

        //assert throws
        assertThatIllegalArgumentException()
                .isThrownBy(() -> voucher.discount(beforeDiscountPrice));
    }

    @Test
    void 할인_성공() {
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