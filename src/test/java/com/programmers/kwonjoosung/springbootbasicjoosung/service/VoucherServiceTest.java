package com.programmers.kwonjoosung.springbootbasicjoosung.service;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherFactory;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherType;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VoucherServiceTest {

    VoucherRepository voucherRepositoryMock = Mockito.mock(VoucherRepository.class);

    VoucherService voucherService = new VoucherService(voucherRepositoryMock);

    @Test
    @DisplayName("바우처를 생성할 수 있다")
    void createAndSave() {
        //given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, voucherId, 1000);
        Voucher savedVoucher;

        //when
        try (MockedStatic<VoucherFactory> utilities = mockStatic(VoucherFactory.class)) {
            utilities.when(() -> VoucherFactory.createVoucher(VoucherType.FIXED, 1000)).thenReturn(voucher);
            when(voucherRepositoryMock.insert(voucher)).thenReturn(voucher);
            savedVoucher = voucherService.createVoucher(VoucherType.FIXED, 1000);
        }

        //then
        verify(voucherRepositoryMock).insert(voucher);
        assertEquals(voucher, savedVoucher);
    }

    @Test
    @DisplayName("바우처를 조회할 수 있다")
    void findVoucher() {
        //given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, voucherId, 1000);
        //when
        when(voucherRepositoryMock.findById(voucherId)).thenReturn(voucher);
        Voucher foundVoucher = voucherService.findVoucher(voucherId);
        //then
        assertThat(foundVoucher).isEqualTo(voucher);
    }

    @Test
    @DisplayName("전체 바우처를 조회할 수 있다")
    void getAllVoucher() {
        //given
        UUID voucherId1 = UUID.randomUUID();
        Voucher voucher1 = VoucherFactory.createVoucher(VoucherType.FIXED, voucherId1, 1000);
        UUID voucherId2 = UUID.randomUUID();
        Voucher voucher2 = VoucherFactory.createVoucher(VoucherType.PERCENT, voucherId2, 10);
        //when
        when(voucherRepositoryMock.findAll()).thenReturn(List.of(voucher1, voucher2));
        List<Voucher> vouchers = voucherService.getAllVouchers();
        //then
        assertThat(voucher1).isEqualTo(vouchers.get(0));
        assertThat(voucher2).isEqualTo(vouchers.get(1));
    }

    @Test
    @DisplayName("바우처를 수정할 수 있다")
    void updateVoucher() {
        //given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.PERCENT, voucherId, 30);
        //when
        when(voucherRepositoryMock.update(voucher)).thenReturn(voucher);
        Voucher foundVoucher = voucherService.updateVoucher(voucher);
        //then
        assertThat(foundVoucher).isEqualTo(voucher);
        verify(voucherRepositoryMock).update(voucher);
    }

    @Test
    @DisplayName("바우처를 삭제할 수 있다")
    void deleteVoucher() {
        //given
        UUID voucherId = UUID.randomUUID();
        //when
        when(voucherRepositoryMock.deleteById(voucherId)).thenReturn(1);
        //then
        assertTrue(voucherService.deleteVoucher(voucherId));
    }

}