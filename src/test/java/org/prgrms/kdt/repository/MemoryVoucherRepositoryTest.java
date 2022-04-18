package org.prgrms.kdt.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.service.VoucherService;

import java.util.UUID;

import static org.mockito.Mockito.*;

class MemoryVoucherRepositoryTest {

    @DisplayName("voucher가 insert 된다.")
    @Test
    void insertTest() {

        MemoryVoucherRepository memoryVoucherRepositoryMock = mock(MemoryVoucherRepository.class);
        VoucherService voucherService = new VoucherService(memoryVoucherRepositoryMock);

        Voucher insertedVoucher = voucherService.createVoucher(UUID.randomUUID(), 1, 100);

        verify(memoryVoucherRepositoryMock).insert(insertedVoucher);
    }
}