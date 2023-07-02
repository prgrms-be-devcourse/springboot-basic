package com.wonu606.vouchermanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.wonu606.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.wonu606.vouchermanager.domain.voucher.PercentageVoucher;
import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.repository.LocalMemoryVoucherRepository;
import com.wonu606.vouchermanager.repository.VoucherRepository;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class VoucherServiceTest {

    @Test
    void 타입이름이_fixed면_FixedAmountVoucher_생성해야_한다() {
        // given
        VoucherRepository repository = new LocalMemoryVoucherRepository();
        VoucherService service = new VoucherService(repository);

        String typeName = "fixed";
        UUID uuid = UUID.randomUUID();
        double discount = 5000;

        // when
        Voucher voucher = service.createVoucher(typeName, uuid, discount);

        // then
        assertEquals(FixedAmountVoucher.class, voucher.getClass());
    }

    @Test
    void 타입이름이_percent면_PercentageVoucher_생성해야_한다() {
        // given
        VoucherRepository repository = new LocalMemoryVoucherRepository();
        VoucherService service = new VoucherService(repository);

        String typeName = "percent";
        UUID uuid = UUID.randomUUID();
        double discount = 50;

        // when
        Voucher voucher = service.createVoucher(typeName, uuid, discount);

        // then
        assertEquals(PercentageVoucher.class, voucher.getClass());
    }

    @Test
    void createVoucher시_객체가_저장되어야_한다() {
        // given
        VoucherRepository repository = new LocalMemoryVoucherRepository();
        VoucherService service = new VoucherService(repository);

        String typeName = "percent";
        UUID uuid = UUID.randomUUID();
        double discount = 50;

        // when
        Voucher voucher = service.createVoucher(typeName, uuid, discount);

        // then
        assertEquals(1, service.getVoucherList().size());
    }
}
