package com.example.kdtspringmission.voucher;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.kdtspringmission.voucher.domain.FixedAmountVoucher;
import com.example.kdtspringmission.voucher.domain.RateAmountVoucher;
import com.example.kdtspringmission.voucher.domain.Voucher;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class VoucherTest {

    @Test
    void testFixedAmountVoucher() {
        //given
        Voucher voucherDiscount100 = new FixedAmountVoucher(UUID.randomUUID(), 100L);
        Voucher voucherDiscount200 = new FixedAmountVoucher(UUID.randomUUID(), 200L);

        //when
        long discount100 = voucherDiscount100.discount(10000L);
        long discount200 = voucherDiscount200.discount(10000L);

        //then
        assertThat(discount100).isEqualTo(9900L);
        assertThat(discount200).isEqualTo(9800L);
    }

    @Test
    void testRateAmountVoucher() {
        // TODO: 소수점 검증
        //given
        Voucher voucherDiscount10 = new RateAmountVoucher(UUID.randomUUID(), 10L);
        Voucher voucherDiscount20 = new RateAmountVoucher(UUID.randomUUID(), 50L);

        //when
        long discount10 = voucherDiscount10.discount(10000L);
        long discount20 = voucherDiscount20.discount(10000L);

        //then
        assertThat(discount10).isEqualTo(9000L);
        assertThat(discount20).isEqualTo(5000L);
    }

}