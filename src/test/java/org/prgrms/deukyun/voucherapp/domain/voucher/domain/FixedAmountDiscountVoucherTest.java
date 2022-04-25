package org.prgrms.deukyun.voucherapp.domain.voucher.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.prgrms.deukyun.voucherapp.domain.testutil.Fixture.fixedAmountDiscountVoucher;

/**
 * FDV - abbreviation of FixedAmountDiscountVoucher
 */
class FixedAmountDiscountVoucherTest {

    @Test
    void 생성_성공() {
        //given
        long amount = 1000L;

        //when
        FixedAmountDiscountVoucher voucher = new FixedAmountDiscountVoucher(amount);

        //then
        assertThat(voucher).isNotNull();
        assertThat(voucher.getId()).isNotNull();
        assertThat(voucher.getAmount()).isEqualTo(amount);
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
        FixedAmountDiscountVoucher voucher = fixedAmountDiscountVoucher();
        long beforeDiscountPrice = 500L;

        //assert throws
        assertThatIllegalArgumentException()
                .isThrownBy(() -> voucher.discount(beforeDiscountPrice));
    }

    @Test
    void 할인_성공() {
        //setup
        FixedAmountDiscountVoucher voucher = fixedAmountDiscountVoucher();
        long beforeDiscountPrice = 3000L;

        //when
        long discountedPrice = voucher.discount(beforeDiscountPrice);

        //assert
        assertThat(discountedPrice).isEqualTo(beforeDiscountPrice - voucher.getAmount());
    }

}