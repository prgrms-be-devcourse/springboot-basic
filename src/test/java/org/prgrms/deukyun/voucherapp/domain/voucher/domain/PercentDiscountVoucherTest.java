package org.prgrms.deukyun.voucherapp.domain.voucher.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.prgrms.deukyun.voucherapp.domain.testutil.Fixture.percentDiscountVoucher;

/**
 * PDV - abbreviation of PercentDiscountVoucher
 */
class PercentDiscountVoucherTest {

    @Test
    void 성공_생성() {
        //setup
        long percent = 20L;

        //when
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(20L);

        //assert
        assertThat(voucher).isNotNull();
        assertThat(voucher.getId()).isNotNull();
        assertThat(voucher.getPercent()).isEqualTo(percent);
    }

    @Test
    void 실패_생성_범위_밖의_퍼센트() {
        //setup
        long percent = 101L;

        //assert throws
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new PercentDiscountVoucher(percent));

    }

    @Test
    void 성공_할인() {
        //setup
        long percent = 20L;
        Voucher voucher = percentDiscountVoucher();
        long beforeDiscountPrice = 1000L;

        //when
        long discountedPrice = voucher.discount(beforeDiscountPrice);

        //assert
        assertThat(discountedPrice).isEqualTo(beforeDiscountPrice * (100 - percent) / 100);
    }
}