package org.prgrms.deukyun.voucherapp.domain.voucher.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

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
        assertThat(voucher.getCustomerId()).isEmpty();
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
    void 실패_생성_정가_가_할인액_초과() {
        //setup
        FixedAmountDiscountVoucher voucher = fixedAmountDiscountVoucher();
        long beforeDiscountPrice = 500L;

        //assert throws
        assertThatIllegalArgumentException()
                .isThrownBy(() -> voucher.discount(beforeDiscountPrice));
    }

    @Test
    void 성공_고객아이디_세팅() {
        //given
        UUID customerId = UUID.randomUUID();
        FixedAmountDiscountVoucher voucher = fixedAmountDiscountVoucher();

        //when
        voucher.setOwnerId(customerId);

        //then
        assertThat(voucher.getCustomerId()).isPresent();
        assertThat(voucher.getCustomerId().get()).isEqualTo(customerId);
    }

    @Test
    void 실패_고객아이디_세팅_아이디가_Null_인경우() {
        //given
        UUID customerId = null;
        FixedAmountDiscountVoucher voucher = fixedAmountDiscountVoucher();

        //then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> voucher.setOwnerId(customerId));
    }

    @Test
    void 성공_할인() {
        //setup
        FixedAmountDiscountVoucher voucher = fixedAmountDiscountVoucher();
        long beforeDiscountPrice = 3000L;

        //when
        long discountedPrice = voucher.discount(beforeDiscountPrice);

        //assert
        assertThat(discountedPrice).isEqualTo(beforeDiscountPrice - voucher.getAmount());
    }

}