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
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class VoucherServiceTest {

    private final VoucherRepository voucherRepositoryMock = Mockito.mock(VoucherRepository.class);
    private final VoucherService voucherService = new VoucherService(voucherRepositoryMock);

    @Test
    @DisplayName("[성공] 바우처를 생성하고 저장할 수 있다")
    void createAndSaveVoucherTest() {
        //given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, voucherId, 1000);
        Optional<Voucher> savedVoucher;
        //when
        try (MockedStatic<VoucherFactory> utilities = mockStatic(VoucherFactory.class)) {
            utilities.when(() -> VoucherFactory.createVoucher(VoucherType.FIXED, 1000)).thenReturn(voucher);
            when(voucherRepositoryMock.insert(voucher)).thenReturn(true);
            savedVoucher = voucherService.createVoucher(VoucherType.FIXED, 1000);
        }
        //then
        verify(voucherRepositoryMock).insert(voucher);
        assertThat(savedVoucher).isNotEmpty();
        assertThat(savedVoucher.get()).isEqualTo(voucher);
    }

    @Test
    @DisplayName("[실패] 같은 바우처를 중복해서 저장할 수 없다")
    void createAndSaveSameVoucherTest() {
        //given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, voucherId, 1000);
        Optional<Voucher> savedVoucher;
        //when
        try (MockedStatic<VoucherFactory> utilities = mockStatic(VoucherFactory.class)) {
            utilities.when(() -> VoucherFactory.createVoucher(VoucherType.FIXED, 1000)).thenReturn(voucher);
            when(voucherRepositoryMock.insert(voucher)).thenReturn(false);
            savedVoucher = voucherService.createVoucher(VoucherType.FIXED, 1000);
        }
        //then
        assertThat(savedVoucher).isEmpty();
        verify(voucherRepositoryMock).insert(voucher);
    }

    @Test
    @DisplayName("[성공] 바우처id를 통해 바우처 정보를 조회할 수 있다")
    void findVoucherByVoucherId() {
        //given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, voucherId, 1000);
        when(voucherRepositoryMock.findById(voucherId)).thenReturn(Optional.of(voucher));
        //when
        Optional<Voucher> foundVoucher = voucherService.findVoucher(voucherId);
        //then
        assertThat(foundVoucher).isNotEmpty();
        assertThat(foundVoucher.get()).isEqualTo(voucher);
        verify(voucherRepositoryMock).findById(voucherId);
    }

    @Test
    @DisplayName("[실패] 바우처id가 없다면 바우처 정보를 조회할 수 없다")
    void findVoucherByNotExistVoucherId() {
        //given
        UUID voucherId = UUID.randomUUID();
        when(voucherRepositoryMock.findById(voucherId)).thenReturn(Optional.empty());
        //when
        Optional<Voucher> foundVoucher = voucherService.findVoucher(voucherId);
        //then
        assertThat(foundVoucher).isEmpty();
        verify(voucherRepositoryMock).findById(voucherId);
    }

    @Test
    @DisplayName("[성공] 현재 테이블에 저장된 모든 바우처를 조회할 수 있다")
    void getAllVoucherFromVoucherTable() {
        //given
        UUID voucherId1 = UUID.randomUUID();
        Voucher voucher1 = VoucherFactory.createVoucher(VoucherType.FIXED, voucherId1, 1000);
        UUID voucherId2 = UUID.randomUUID();
        Voucher voucher2 = VoucherFactory.createVoucher(VoucherType.PERCENT, voucherId2, 10);
        when(voucherRepositoryMock.findAll()).thenReturn(List.of(voucher1, voucher2));
        //when
        List<Voucher> vouchers = voucherService.getAllVouchers();
        //then
        assertThat(vouchers).isNotEmpty();
        assertThat(vouchers).containsExactly(voucher1, voucher2);
        assertThat(voucher1).isEqualTo(vouchers.get(0));
        assertThat(voucher2).isEqualTo(vouchers.get(1));
        verify(voucherRepositoryMock).findAll();
    }

    @Test
    @DisplayName("[성공] 현재 테이블에 저장된 바우처가 없다면 바우처를 조회할 수 없다")
    void getAllVoucherFromEmpty() {
        //given
        when(voucherRepositoryMock.findAll()).thenReturn(List.of());
        //when
        List<Voucher> vouchers = voucherService.getAllVouchers();
        //then
        assertThat(vouchers).isEmpty();
        verify(voucherRepositoryMock).findAll();
    }

    @Test
    @DisplayName("[성공] 바우처 테이블에 존재하는 바우처는 수정할 수 있다")
    void updateVoucherInfo() {
        //given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.PERCENT, voucherId, 30);
        when(voucherRepositoryMock.findById(voucherId)).thenReturn(Optional.of(voucher));
        when(voucherRepositoryMock.update(voucher)).thenReturn(true);
        //when
        boolean updateResult;
        try (MockedStatic<VoucherFactory> utilities = mockStatic(VoucherFactory.class)) {
            utilities.when(() -> VoucherFactory.createVoucher(VoucherType.FIXED,voucherId, 1000)).thenReturn(voucher);
            updateResult = voucherService.updateVoucher(voucher.getVoucherId(), VoucherType.FIXED, 1000);
        }
        //then
        assertThat(updateResult).isTrue();
        verify(voucherRepositoryMock).findById(voucherId);
        verify(voucherRepositoryMock).update(voucher);
    }

    @Test
    @DisplayName("[실패] 바우처 테이블에 존재하지 않는 바우처는 수정할 수 없다")
    void updateNotExistVoucher() {
        //given
        UUID voucherId = UUID.randomUUID();
        when(voucherRepositoryMock.findById(voucherId)).thenReturn(Optional.empty());
        //when
        boolean updateResult= voucherService.updateVoucher(voucherId, VoucherType.FIXED, 1000);
        //then
        assertThat(updateResult).isFalse();
        verify(voucherRepositoryMock).findById(voucherId);
    }

    @Test
    @DisplayName("[성공] 바우처를 삭제할 수 있다")
    void deleteVoucher() {
        //given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, voucherId, 1000);
        when(voucherRepositoryMock.findById(voucherId)).thenReturn(Optional.of(voucher));
        when(voucherRepositoryMock.deleteById(voucherId)).thenReturn(true);
        //when
        boolean deleteResult = voucherService.deleteVoucher(voucherId);
        //then
        assertThat(deleteResult).isTrue();
        verify(voucherRepositoryMock).deleteById(voucherId);
    }

    @Test
    @DisplayName("[실패] 존재하지 않는 바우처를 삭제할 수 없다")
    void deleteVoucherNotExist() {
        //given
        UUID voucherId = UUID.randomUUID();
        when(voucherRepositoryMock.findById(voucherId)).thenReturn(Optional.empty());
        //when
        boolean deleteResult = voucherService.deleteVoucher(voucherId);
        //then
        assertThat(deleteResult).isFalse();
        verify(voucherRepositoryMock).findById(voucherId);
    }

}