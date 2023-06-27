package com.wonu606.vouchermanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wonu606.vouchermanager.domain.FixedAmountVoucher;
import com.wonu606.vouchermanager.domain.PercentageVoucher;
import com.wonu606.vouchermanager.domain.Voucher;
import com.wonu606.vouchermanager.domain.VoucherVO;
import com.wonu606.vouchermanager.repository.LocalMemoryVoucherRepository;
import com.wonu606.vouchermanager.repository.VoucherRepository;
import com.wonu606.vouchermanager.service.factory.VoucherFactory;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VoucherServiceTest {

    @Test
    @DisplayName("VO.type == fixed라면 FixedAmountVoucher를 생성한다.")
    void createFixedAmountVoucher() {
        // given
        VoucherVO voucherVO = new VoucherVO("fixed", 50.0d);
        VoucherService voucherService = new VoucherService(new LocalMemoryVoucherRepository());

        // then
        Voucher voucher = voucherService.createVoucher(voucherVO);

        // when
        assertEquals(FixedAmountVoucher.class, voucher.getClass());
    }

    @Test
    @DisplayName("VO.type == percentage라면 PercentageVoucher를 생성한다.")
    void createPercentageVoucher() {
        // given
        VoucherVO voucherVO = new VoucherVO("percentage", 50.0d);
        VoucherService voucherService = new VoucherService(new LocalMemoryVoucherRepository());

        // then
        Voucher voucher = voucherService.createVoucher(voucherVO);

        // when
        assertEquals(PercentageVoucher.class, voucher.getClass());
    }

    @Test
    @DisplayName("존재하지 않는 타입이라면 IllegalArgumentException 예외 발생한다.")
    void createSomethingDontKnow() {
        // given
        VoucherVO voucherVO = new VoucherVO("aaa", 50.0d);
        VoucherService voucherService = new VoucherService(new LocalMemoryVoucherRepository());

        // then & when
        assertThrows(IllegalArgumentException.class, () -> voucherService.createVoucher(voucherVO));
    }

    @Test
    void getVoucherTypes() {
        // given
        VoucherService voucherService = new VoucherService(new LocalMemoryVoucherRepository());
        VoucherFactory voucherFactory = new VoucherFactory();

        // when
        List<String> expectedVoucherTypes = voucherService.getVoucherTypes();

        // then
        assertEquals(new ArrayList<>(voucherFactory.getCreatableVoucherTypes()),
                expectedVoucherTypes);
    }

    @Test
    void getVoucherList() {
        // given
        VoucherVO voucherVO1 = new VoucherVO("fixed", 50.0d);
        VoucherVO voucherVO2 = new VoucherVO("percentage", 50.0d);

        VoucherRepository voucherRepository = new LocalMemoryVoucherRepository();
        VoucherService voucherService = new VoucherService(voucherRepository);

        // when
        voucherService.createVoucher(voucherVO1);
        voucherService.createVoucher(voucherVO2);

        // then
        assertEquals(voucherRepository.findAll(), voucherService.getVoucherList());
    }
}