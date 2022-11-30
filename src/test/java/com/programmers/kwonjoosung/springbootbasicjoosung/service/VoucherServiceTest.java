package com.programmers.kwonjoosung.springbootbasicjoosung.service;

import com.programmers.kwonjoosung.springbootbasicjoosung.controller.voucher.request.CreateVoucherRequest;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherFactory;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherType;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher.VoucherRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class VoucherServiceTest {

    private final VoucherRepository voucherRepositoryMock = Mockito.mock(VoucherRepository.class);
    private final VoucherService voucherService = new VoucherService(voucherRepositoryMock);

    @Test
    @DisplayName("[성공] 바우처를 생성하고 저장하는 기능을 테스트 한다")
    void createAndSaveVoucherTest() {
        //given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, voucherId, 1000);
        //when
        try (MockedStatic<VoucherFactory> utilities = mockStatic(VoucherFactory.class)) {
            utilities.when(() -> VoucherFactory.createVoucher(VoucherType.FIXED, 1000)).thenReturn(voucher);
            when(voucherRepositoryMock.insert(voucher)).thenReturn(voucher);
            voucherService.saveVoucher(new CreateVoucherRequest(VoucherType.FIXED.name(), 1000));
            utilities.verify(() -> VoucherFactory.createVoucher(VoucherType.FIXED, 1000));
        }
        //then
        verify(voucherRepositoryMock).insert(voucher);
    }

    @Test
    @DisplayName("[성공] 바우처id를 통해 바우처 정보를 조회하는 기능을 테스트 한다")
    void findVoucherByVoucherId() {
        //given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, voucherId, 1000);
        when(voucherRepositoryMock.findById(voucherId)).thenReturn(Optional.of(voucher));
        //when
        voucherService.findVoucher(voucherId);
        //then
        verify(voucherRepositoryMock).findById(voucherId);
    }

    @Test
    @DisplayName("[성공] 현재 테이블에 저장된 모든 바우처를 조회하는 기능을 테스트 한다")
    void getAllVoucherFromVoucherTable() {
        //given
        UUID voucherId1 = UUID.randomUUID();
        Voucher voucher1 = VoucherFactory.createVoucher(VoucherType.FIXED, voucherId1, 1000);
        UUID voucherId2 = UUID.randomUUID();
        Voucher voucher2 = VoucherFactory.createVoucher(VoucherType.PERCENT, voucherId2, 10);
        when(voucherRepositoryMock.findAll()).thenReturn(List.of(voucher1, voucher2));
        //when
        voucherService.getAllVouchers();
        //then
        verify(voucherRepositoryMock).findAll();
    }

    @Test
    @DisplayName("[성공] 바우처 테이블에 존재하는 바우처는 수정하는 기능을 테스트 한다.")
    void updateVoucherInfo() {
        //given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.PERCENT, voucherId, 30);
        when(voucherRepositoryMock.update(voucher)).thenReturn(voucher);
        //when

        try (MockedStatic<VoucherFactory> utilities = mockStatic(VoucherFactory.class)) {
            utilities.when(() -> VoucherFactory.createVoucher(VoucherType.FIXED,voucherId, 1000)).thenReturn(voucher);
            voucherService.updateVoucher(voucher.getVoucherId(), VoucherType.FIXED, 1000);
        }
        //then
        verify(voucherRepositoryMock).update(voucher);
    }

    @Test
    @DisplayName("[성공] 바우처를 삭제하는 기능을 테스트 한다.")
    void deleteVoucher() {
        //given
        UUID voucherId = UUID.randomUUID();
        //when
        voucherService.deleteVoucher(voucherId);
        //then
        verify(voucherRepositoryMock).deleteById(voucherId);
    }

}