package com.zerozae.voucher.service;

import com.zerozae.voucher.domain.voucher.FixedDiscountVoucher;
import com.zerozae.voucher.domain.voucher.PercentDiscountVoucher;
import com.zerozae.voucher.domain.voucher.UseStatusType;
import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.dto.voucher.VoucherRequest;
import com.zerozae.voucher.dto.voucher.VoucherResponse;
import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;
import com.zerozae.voucher.exception.ErrorMessage;
import com.zerozae.voucher.repository.voucher.VoucherRepository;
import com.zerozae.voucher.service.voucher.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.zerozae.voucher.domain.voucher.VoucherType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VoucherServiceTest {

    VoucherRepository voucherRepository;
    VoucherService voucherService;
    FixedDiscountVoucher fixedDiscountVoucher;
    PercentDiscountVoucher percentDiscountVoucher;

    @BeforeEach
    void setUp() {
        voucherRepository = mock(VoucherRepository.class);
        voucherService = new VoucherService(voucherRepository);

        fixedDiscountVoucher = new FixedDiscountVoucher(10L);
        percentDiscountVoucher = new PercentDiscountVoucher(10L);
    }

    @Test
    @DisplayName("바우처 등록 메서드 호출 테스트")
    void createVoucher_Success_Test() {
        // Given
        VoucherRequest voucherRequest = new VoucherRequest(10L, FIXED);

        when(voucherRepository.save(fixedDiscountVoucher)).thenReturn(fixedDiscountVoucher);

        // When
        VoucherResponse voucherResponse = voucherService.createVoucher(voucherRequest);

        // Then
        assertEquals(10L, voucherResponse.getDiscount());
        assertEquals(voucherResponse.getVoucherType(), FIXED);
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
    @DisplayName("회원에게 바우처 할당 테스트")
    void registerVoucher_Success_Test() {
        // Given
        UUID customerId = UUID.randomUUID();
        UUID voucherId = fixedDiscountVoucher.getVoucherId();
        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(fixedDiscountVoucher));

        // Then
        voucherService.registerVoucher(customerId,voucherId);

        // When
        verify(voucherRepository, times(1)).findById(voucherId);
        verify(voucherRepository, times(1)).registerVoucher(customerId,voucherId);
    }

    @Test
    @DisplayName("회원에게 바우처 할당 실패 (바우처 존재 X) 테스트")
    void registerVoucher_VoucherNotFound_Failed_Test() {
        UUID customerId = UUID.randomUUID();
        UUID notExistId = UUID.randomUUID();
        when(voucherRepository.findById(notExistId)).thenReturn(Optional.empty());

        // Then
        assertThrows(ErrorMessage.class , () -> {
            voucherService.registerVoucher(customerId, notExistId);
        });
        verify(voucherRepository, times(1)).findById(notExistId);
    }

    @Test
    @DisplayName("회원에게 바우처 할당 실패 (중복 등록) 테스트")
    void registerVoucher_Duplicated_Failed_Test() {
        UUID customerId = UUID.randomUUID();
        UUID voucherId = fixedDiscountVoucher.getVoucherId();

        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(fixedDiscountVoucher));
        when(voucherRepository.findVoucherOwner(voucherId)).thenReturn(Optional.of(customerId));

        // Then
        assertThrows(ErrorMessage.class , () -> {
            voucherService.registerVoucher(customerId, voucherId);
        });
        verify(voucherRepository, times(1)).findById(voucherId);
        verify(voucherRepository, times(1)).findVoucherOwner(voucherId);
    }

    @Test
    @DisplayName("회원에게 할당한 바우처 목록 조회 테스트")
    void findHaveVouchers_Success_Test() {
        // Given
        UUID ownerId = UUID.randomUUID();
        List<Voucher> haveVouchers = List.of(fixedDiscountVoucher, percentDiscountVoucher);

        when(voucherRepository.findVouchersByCustomerId(ownerId)).thenReturn(haveVouchers);

        // When
        List<VoucherResponse> vouchers = voucherService.findHaveVouchers(ownerId);

        // Then
        assertTrue(vouchers.stream().anyMatch(voucher -> voucher.getDiscount() == fixedDiscountVoucher.getDiscount()));
        assertTrue(vouchers.stream().anyMatch(voucher -> voucher.getDiscount() == percentDiscountVoucher.getDiscount()));
        verify(voucherRepository,times(1)).findVouchersByCustomerId(ownerId);
    }

    @Test
    @DisplayName("회원에게 할당한 바우처 제거 호출 테스트")
    void removeVoucherFromCustomer_Success_Test() {
        // Given
        UUID voucherId = fixedDiscountVoucher.getVoucherId();

        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(fixedDiscountVoucher));

        // When
        voucherService.removeVoucherFromCustomer(voucherId);

        // Then
        verify(voucherRepository, times(1)).findById(voucherId);
        verify(voucherRepository, times(1)).removeVoucher(voucherId);
    }

    @Test
    @DisplayName("존재하지 않는 바우처 아이디로 회원 할당 바우처 제거 예외 테스트")
    void removeVoucherFromCustomer_NotExistId_Failed_Test() {
        // Given
        UUID notExistId = UUID.randomUUID();

        when(voucherRepository.findById(notExistId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ErrorMessage.class, () -> {
            voucherService.removeVoucherFromCustomer(notExistId);
        });
        verify(voucherRepository, times(1)).findById(notExistId);
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
        assertEquals(findVoucher.getVoucherId() , fixedDiscountVoucher.getVoucherId().toString());
    }

    @Test
    @DisplayName("바우처 아이디로 바우처 조회 실패 테스트")
    void findById_NotExistId_failed_Test() {
        // Given
        UUID notExistId = UUID.randomUUID();

        when(voucherRepository.findById(notExistId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ErrorMessage.class, () -> {
            VoucherResponse findVoucher = voucherService.findById(notExistId);
        });
        verify(voucherRepository, times(1)).findById(notExistId);
    }

    @Test
    @DisplayName("바우처 소유자 아이디 조회 테스트")
    void findVoucherOwner_Success_Test() {
        // Given
        UUID voucherId = fixedDiscountVoucher.getVoucherId();
        UUID customerId = UUID.randomUUID();
        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(fixedDiscountVoucher));
        when(voucherRepository.findVoucherOwner(voucherId)).thenReturn(Optional.of(customerId));

        // When
        Optional<UUID> voucherOwner = voucherRepository.findVoucherOwner(voucherId);

        // Then
        assertTrue(voucherOwner.isPresent());
        assertEquals(voucherOwner.get().toString(), customerId.toString());
    }

    @Test
    @DisplayName("바우처 소유자 아이디 조회 실패 테스트 (바우처 존재 X)")
    void findOwnerId_NotExistIdVoucher_Failed_Test() {
        // Given
        UUID notExistId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        when(voucherRepository.findById(notExistId)).thenReturn(Optional.empty());

        // When
        assertThrows(ErrorMessage.class, () -> {
            voucherService.findVoucherOwnerId(notExistId);
        });
        verify(voucherRepository, times(1)).findById(notExistId);
    }

    @Test
    @DisplayName("바우처 소유자 아이디 조회 실패 테스트 (소유자 존재 X)")
    void findOwnerId_NotExistOwner_Failed_Test() {
        // Given
        UUID voucherId = fixedDiscountVoucher.getVoucherId();
        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(fixedDiscountVoucher));
        when(voucherRepository.findVoucherOwner(voucherId)).thenReturn(Optional.empty());

        // When
        assertThrows(ErrorMessage.class, () -> {
            voucherService.findVoucherOwnerId(voucherId);
        });
        verify(voucherRepository, times(1)).findById(voucherId);
        verify(voucherRepository, times(1)).findVoucherOwner(voucherId);
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
        assertThrows(ErrorMessage.class, () -> {
            voucherService.deleteById(notExistId);
        });

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
        VoucherUpdateRequest voucherUpdateRequest = new VoucherUpdateRequest(20L, UseStatusType.UNAVAILABLE);

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
        VoucherUpdateRequest voucherUpdateRequest = new VoucherUpdateRequest(20L, UseStatusType.UNAVAILABLE);
        when(voucherRepository.findById(notExistId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ErrorMessage.class, () -> {
            voucherService.update(notExistId, voucherUpdateRequest);
        });
        verify(voucherRepository, times(1)).findById(notExistId);
    }
}
