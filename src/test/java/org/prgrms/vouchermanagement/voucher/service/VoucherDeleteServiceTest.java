package org.prgrms.vouchermanagement.voucher.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanagement.voucher.repository.VoucherRepository;

import java.util.UUID;

import static org.mockito.Mockito.*;

class VoucherDeleteServiceTest {

    private final VoucherRepository voucherRepository = mock(VoucherRepository.class);
    private final VoucherDeleteService voucherDeleteService = new VoucherDeleteService(voucherRepository);

    @Test
    @DisplayName("CustomerId로 바우처 삭제 시 VoucherRepository 동작 확인")
    void voucherList() {
        // given
        UUID customerId = UUID.randomUUID();

        doNothing()
                .when(voucherRepository).deleteVoucherByCustomerId(customerId);

        // when
        voucherDeleteService.deleteVouchersByCustomerId(customerId);

        // then
        verify(voucherRepository).deleteVoucherByCustomerId(customerId);
    }
}