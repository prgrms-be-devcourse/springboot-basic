package com.wonu606.vouchermanager.service.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wonu606.vouchermanager.domain.FixedAmountVoucher;
import com.wonu606.vouchermanager.domain.PercentageVoucher;
import com.wonu606.vouchermanager.domain.Voucher;
import com.wonu606.vouchermanager.domain.VoucherVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VoucherFactoryTest {

    @Test
    @DisplayName("VO.type == fixed라면 FixedAmountVoucher를 생성한다.")
    void createFixedAmountVoucher() {
        // given
        VoucherVO voucherVO = new VoucherVO("fixed", 50.0d);
        VoucherFactory voucherFactory = new VoucherFactory();

        // then
        Voucher voucher = voucherFactory.createVoucher(voucherVO);

        // when
        assertEquals(FixedAmountVoucher.class, voucher.getClass());
    }

    @Test
    @DisplayName("VO.type == percentage라면 PercentageVoucher를 생성한다.")
    void createPercentageVoucher() {
        // given
        VoucherVO voucherVO = new VoucherVO("percentage", 50.0d);
        VoucherFactory voucherFactory = new VoucherFactory();

        // then
        Voucher voucher = voucherFactory.createVoucher(voucherVO);

        // when
        assertEquals(PercentageVoucher.class, voucher.getClass());
    }

    @Test
    @DisplayName("존재하지 않는 타입이라면 IllegalArgumentException 예외 발생한다.")
    void createSomethingDontKnow() {
        // given
        VoucherVO voucherVO = new VoucherVO("aaa", 50.0d);
        VoucherFactory voucherFactory = new VoucherFactory();

        // then & when
        assertThrows(IllegalArgumentException.class, () -> voucherFactory.createVoucher(voucherVO));
    }

    @Test
    void getCreatableVoucherTypes() {
        // todo
        // given
        // then
        // when
    }
}