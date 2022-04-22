package me.programmers.springboot.basic.springbootbasic.voucher.service;

import me.programmers.springboot.basic.springbootbasic.voucher.model.FixedAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.PercentAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VoucherServiceTest {

    @Test
    @DisplayName("바우처를 생성한다")
    void createVoucher() {
        VoucherService voucherServiceMock = mock(VoucherService.class);
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);

        when(voucherServiceMock.save(voucher)).thenReturn(voucher);
    }

    @Test
    @DisplayName("모든 바우처를 조회한다.")
    void showVoucherList() {
        VoucherService voucherServiceMock = mock(VoucherService.class);
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 100);
        Voucher voucher2 = new PercentAmountVoucher(UUID.randomUUID(), 10);
        Voucher voucher3 = new FixedAmountVoucher(UUID.randomUUID(), 20);

        voucherServiceMock.save(voucher1);
        voucherServiceMock.save(voucher2);
        voucherServiceMock.save(voucher3);

        when(voucherServiceMock.getVouchers()).thenReturn(List.of(voucher1, voucher2, voucher3));
    }

}