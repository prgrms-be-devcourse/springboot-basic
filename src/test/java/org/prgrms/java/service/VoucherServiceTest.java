package org.prgrms.java.service;

import org.junit.jupiter.api.*;
import org.prgrms.java.domain.voucher.FixedAmountVoucher;
import org.prgrms.java.domain.voucher.PercentDiscountVoucher;
import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.exception.VoucherException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class VoucherServiceTest {
    private final VoucherService voucherService = mock(VoucherService.class);

    @Test
    @DisplayName("서비스를 통해 바우처를 등록할 수 있다.")
    void testCreateVoucher() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10000, LocalDateTime.now(), LocalDateTime.now());
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 50, LocalDateTime.now(), LocalDateTime.now());

        voucherService.saveVoucher(fixedAmountVoucher);
        voucherService.saveVoucher(percentDiscountVoucher);

        verify(voucherService).saveVoucher(fixedAmountVoucher);
        verify(voucherService).saveVoucher(percentDiscountVoucher);
    }

    @Test
    @DisplayName("서비스를 통해 바우를 조회할 수 있다.")
    void testGetVoucher() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10000, LocalDateTime.now(), LocalDateTime.now());

        voucherService.saveVoucher(fixedAmountVoucher);
        Voucher insertedVoucher = voucherService.getVoucher(fixedAmountVoucher.getVoucherId());

        verify(voucherService).getVoucher(fixedAmountVoucher.getVoucherId());
    }

    @Test
    @DisplayName("존재하지 않는 바우처를 조회하면 예외가 발생한다.")
    void testGetNonExistVoucher() {
        when(voucherService.getVoucher(any())).thenThrow(VoucherException.class);

        Assertions.assertThrows(VoucherException.class, () -> voucherService.getVoucher(UUID.randomUUID()));
    }

    @Test
    @DisplayName("바우처를 등록하지 않으면 전체 조회시 빈 컬렉션이 반환된다.")
    void testGetAllVoucherWithNoCreation() {
        assertThat(voucherService.getAllVouchers(), hasSize(0));
    }

    @Test
    @DisplayName("서비스를 통해 모든 바우처를 조회할 수 있다.")
    void testGetAllVouchers() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10000, LocalDateTime.now(), LocalDateTime.now());
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 50, LocalDateTime.now(), LocalDateTime.now());

        when(voucherService.getAllVouchers())
                .thenReturn(List.of(fixedAmountVoucher, percentDiscountVoucher));

        assertThat(voucherService.getAllVouchers(), hasSize(2));
        assertThat(voucherService.getAllVouchers(), containsInAnyOrder(samePropertyValuesAs(fixedAmountVoucher), samePropertyValuesAs(percentDiscountVoucher)));
    }
}
