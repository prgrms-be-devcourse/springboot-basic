package org.prgrms.deukyun.voucherapp.domain.voucher.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class VoucherFactoryTest {

    VoucherFactory voucherFactory;

    @BeforeEach
    void setUp() {
        voucherFactory = new VoucherFactory();
    }

    @Test
    void 정액_할인_바우처_생성_성공() {
        //given
        String type = "fixed";
        long argument = 2000L;

        //when
        Voucher voucher = voucherFactory.createVoucher(type, argument);

        //then
        assertThat(voucher).isNotNull();
        assertThat(voucher).isInstanceOf(FixedAmountDiscountVoucher.class);

        FixedAmountDiscountVoucher fixedVoucher = (FixedAmountDiscountVoucher) voucher;
        assertThat(fixedVoucher.getAmount()).isEqualTo(argument);
    }

    @Test
    void 정률_할인_바우처_생성_성공() {
        //given
        String type = "percent";
        long argument = 20L;

        //when
        Voucher voucher = voucherFactory.createVoucher(type, argument);

        //then
        assertThat(voucher).isNotNull();
        assertThat(voucher).isInstanceOf(PercentDiscountVoucher.class);

        PercentDiscountVoucher percentVoucher = (PercentDiscountVoucher) voucher;
        assertThat(percentVoucher.getPercent()).isEqualTo(argument);
    }

    @Test
    void 정액_할인_바우처_생성_실패_negativeArgument() {
        //given
        String type = "invalid type";
        long argument = -1L;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> voucherFactory.createVoucher(type, argument));
    }

    @Test
    void 정률_할인_바우처_생성_실패_outOfRangeArgument() {
        //given
        String type = "percent";
        long argument = 101L;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> voucherFactory.createVoucher(type, argument));
    }

    @Test
    void 바우처_생성_실패_invalidType() {
        //given
        String type = "invalid type";
        long argument = 20L;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> voucherFactory.createVoucher(type, argument));
    }
}