package com.zerozae.voucher.service.voucher;

import com.zerozae.voucher.domain.voucher.FixedDiscountVoucher;
import com.zerozae.voucher.domain.voucher.PercentDiscountVoucher;
import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.domain.voucher.VoucherType;
import com.zerozae.voucher.dto.voucher.VoucherCondition;
import com.zerozae.voucher.dto.voucher.VoucherCreateRequest;
import com.zerozae.voucher.dto.voucher.VoucherResponse;
import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;
import com.zerozae.voucher.exception.ExceptionMessage;
import com.zerozae.voucher.repository.voucher.VoucherRepository;
import com.zerozae.voucher.repository.wallet.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.zerozae.voucher.domain.voucher.UseStatusType.UNAVAILABLE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VoucherServiceTest {

    private final VoucherRepository voucherRepository;
    private final WalletRepository walletRepository;
    private final VoucherService voucherService;
    private FixedDiscountVoucher fixedDiscountVoucher;
    private PercentDiscountVoucher percentDiscountVoucher;

    VoucherServiceTest() {
        this.voucherRepository = mock(VoucherRepository.class);
        this.walletRepository = mock(WalletRepository.class);
        this.voucherService = new VoucherService(voucherRepository, walletRepository);
    }

    @BeforeEach
    void setUp() {
        fixedDiscountVoucher = new FixedDiscountVoucher(10L);
        percentDiscountVoucher = new PercentDiscountVoucher(10L);
    }

    @Test
    @DisplayName("바우처 등록 메서드 호출 성공 테스트")
    void createVoucher_Success_Test() {
        // Given
        VoucherCreateRequest voucherRequest = new VoucherCreateRequest(10L, String.valueOf(VoucherType.FIXED));

        when(voucherRepository.save(any(Voucher.class))).thenReturn(fixedDiscountVoucher);

        // When
        VoucherResponse voucherResponse = voucherService.createVoucher(voucherRequest);

        // Then
        assertEquals(voucherResponse.getDiscount(), fixedDiscountVoucher.getDiscount());
        assertEquals(voucherResponse.getVoucherType(), VoucherType.FIXED);
    }

    @Test
    @DisplayName("바우처 리스트 조회 메서드 호출 테스트")
    void findAllVouchers_Success_Test() {
        // Given
        List<Voucher> voucherList = List.of(
            fixedDiscountVoucher,
            percentDiscountVoucher
        );
        when(voucherRepository.findAll()).thenReturn(voucherList);

        // When
        List<VoucherResponse> vouchers = voucherService.findAllVouchers();

        // Then
        assertEquals(vouchers.size() , 2);
        assertTrue(vouchers.stream().anyMatch(voucher -> voucher.getDiscount() == fixedDiscountVoucher.getDiscount()));
        assertTrue(vouchers.stream().anyMatch(voucher -> voucher.getDiscount() == percentDiscountVoucher.getDiscount()));
        verify(voucherRepository,times(1)).findAll();
    }

    @Test
    @DisplayName("바우처 아이디로 바우처 조회 테스트")
    void findById_Success_Test() {
        // Given
        UUID voucherId = fixedDiscountVoucher.getVoucherId();

        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(fixedDiscountVoucher));

        // When
        VoucherResponse findVoucher = voucherService.findById(voucherId);

        // Then
        assertEquals(findVoucher.getDiscount(), fixedDiscountVoucher.getDiscount());
        assertEquals(findVoucher.getVoucherId() , fixedDiscountVoucher.getVoucherId());
    }

    @Test
    @DisplayName("바우처 아이디로 바우처 조회 실패 테스트")
    void findById_NotExistId_failed_Test() {
        // Given
        UUID notExistId = UUID.randomUUID();

        when(voucherRepository.findById(notExistId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ExceptionMessage.class, () -> voucherService.findById(notExistId));
        verify(voucherRepository, times(1)).findById(notExistId);
    }

    @Test
    @DisplayName("바우처 아이디로 바우처 삭제 테스트")
    void deleteById_success_Test() {
        // Given
        UUID voucherId = fixedDiscountVoucher.getVoucherId();
        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(fixedDiscountVoucher));

        // When
        voucherService.deleteById(voucherId);

        // Then
        verify(voucherRepository, times(1)).findById(voucherId);
        verify(voucherRepository, times(1)).deleteById(voucherId);
    }

    @Test
    @DisplayName("바우처 아이디로 바우처 삭제 실패 테스트")
    void deleteById_NotExistVoucher_Failed_Test() {
        // Given
        UUID notExistId = UUID.randomUUID();
        when(voucherRepository.findById(notExistId)).thenReturn(Optional.empty());

        // When
        assertThrows(ExceptionMessage.class, () -> voucherService.deleteById(notExistId));

        // Then
        verify(voucherRepository, times(1)).findById(notExistId);
    }

    @Test
    @DisplayName("바우처 전체 삭제 테스트")
    void deleteAll_success_Test() {
        // Given

        // When
        voucherRepository.deleteAll();

        // Then
        verify(voucherRepository, times(1)).deleteAll();
    }

    @Test
    @DisplayName("바우처 업데이트 테스트")
    void updateVoucher_success_Test() {
        // Given
        UUID voucherId = fixedDiscountVoucher.getVoucherId();
        VoucherUpdateRequest voucherUpdateRequest = new VoucherUpdateRequest(20L, String.valueOf(UNAVAILABLE));

        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(fixedDiscountVoucher));

        // When
        voucherService.update(voucherId, voucherUpdateRequest);

        // Then
        verify(voucherRepository, times(1)).findById(voucherId);
        verify(voucherRepository, times(1)).update(voucherId, voucherUpdateRequest);
    }

    @Test
    @DisplayName("바우처 업데이트 실패 테스트")
    void updateVoucher_NotExistVoucher_Failed_Test() {
        // Given
        UUID notExistId = UUID.randomUUID();
        VoucherUpdateRequest voucherUpdateRequest = new VoucherUpdateRequest(20L, String.valueOf(UNAVAILABLE));
        when(voucherRepository.findById(notExistId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ExceptionMessage.class, () -> voucherService.update(notExistId, voucherUpdateRequest));
        verify(voucherRepository, times(1)).findById(notExistId);
    }

    @Test
    @DisplayName("바우처 아이디로 삭제에 따른 지갑 삭제 호출 테스트")
    void deleteVoucherByIdWithWallets_Success_Test() {
        // Given
        when(voucherRepository.findById(fixedDiscountVoucher.getVoucherId())).thenReturn(Optional.of(fixedDiscountVoucher));

        // When
        voucherService.deleteById(fixedDiscountVoucher.getVoucherId());

        // Then
        verify(voucherRepository).findById(fixedDiscountVoucher.getVoucherId());
        verify(voucherRepository).deleteById(fixedDiscountVoucher.getVoucherId());
        verify(walletRepository).deleteByVoucherId(fixedDiscountVoucher.getVoucherId());
    }

    @Test
    @DisplayName("바우처 전체 삭제에 따른 지갑 삭제 호출 테스트")
    void deleteAllVouchersWithWallets_Success_Test() {
        // Given

        // When
        voucherService.deleteAll();

        // Then
        verify(voucherRepository).deleteAll();
        verify(walletRepository).deleteAll();
    }

    @Test
    @DisplayName("바우처 검색 조건(바우처 타입)에 따른 검색 테스트")
    void findVoucherByVoucherTypeCondition_Success_Test() {
        // Given
        VoucherCondition voucherCondition = new VoucherCondition(String.valueOf(fixedDiscountVoucher.getVoucherType()), null);
        when(voucherRepository.findVoucherByCondition(voucherCondition)).thenReturn(List.of(fixedDiscountVoucher));

        // When
        voucherService.findVoucherByCondition(voucherCondition);

        // Then
        verify(voucherRepository).findVoucherByCondition(voucherCondition);
    }

    @Test
    @DisplayName("바우처 검색 조건(생성 날짜)에 따른 검색 테스트")
    void findVoucherByCreatedAtCondition_Success_Test() {
        // Given
        VoucherCondition voucherCondition = new VoucherCondition(null, LocalDate.now().toString());
        when(voucherRepository.findVoucherByCondition(voucherCondition)).thenReturn(List.of(fixedDiscountVoucher, percentDiscountVoucher));

        // When
        voucherService.findVoucherByCondition(voucherCondition);

        // Then
        verify(voucherRepository).findVoucherByCondition(voucherCondition);
    }

    @Test
    @DisplayName("바우처 검색 조건(생성 날짜)에 따른 검색 테스트")
    void findVoucherByCondition_Success_Test() {
        // Given
        VoucherCondition voucherCondition = new VoucherCondition(fixedDiscountVoucher.getVoucherType().toString(), LocalDate.now().toString());
        when(voucherRepository.findVoucherByCondition(voucherCondition)).thenReturn(List.of(fixedDiscountVoucher));

        // When
        voucherService.findVoucherByCondition(voucherCondition);

        // Then
        verify(voucherRepository).findVoucherByCondition(voucherCondition);
    }
}
