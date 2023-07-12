package com.example.demo.voucher.application;

import com.example.demo.voucher.domain.Voucher;
import com.example.demo.voucher.domain.repository.VoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @Mock
    private VoucherRepository voucherRepository;

    private VoucherService voucherService;

    @BeforeEach
    void setUp() {
        voucherService = new VoucherService(voucherRepository);
    }

    @Test
    @DisplayName("Voucher 아이디로 검색 테스트")
    void getVoucher() {
        // Given
        UUID voucherId = UUID.randomUUID();
        long value = 100L;
        Voucher mockVoucher = VoucherType.FIXED_AMOUNT_VOUCHER.createVoucher(voucherId, value);

        // When
        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(mockVoucher));
        Voucher actual = voucherService.getVoucher(voucherId);

        // Then
        assertNotNull(actual);
        assertEquals(mockVoucher.getVoucherId(), actual.getVoucherId());

        verify(voucherRepository, times(1)).findById(voucherId);
    }

    @Test
    @DisplayName("FIXED_AMOUNT_VOUCHER 생성 확인 테스트")
    void fixedCreate() {
        // Given
        long value = 100;
        VoucherType voucherType = VoucherType.FIXED_AMOUNT_VOUCHER;

        // When
        voucherService.createVoucher(voucherType, value);

        // Then
        verify(voucherRepository, times(1)).insert(any(Voucher.class));
    }

    @Test
    @DisplayName("PERCENT_AMOUNT_VOUCHER 생성 확인 테스트")
    void percentCreate() {
        // Given
        long value = 99;
        VoucherType voucherType = VoucherType.PERCENT_DISCOUNT_VOUCHER;

        // When
        voucherService.createVoucher(voucherType, value);

        // Then
        verify(voucherRepository, times(1)).insert(any(Voucher.class));
    }

    @Test
    @DisplayName("모든 바우처 조회 테스트")
    void listVouchers() {
        // Given
        List<Voucher> mockVouchers = new ArrayList<>();
        UUID voucherId = UUID.randomUUID();
        long value = 99;

        Voucher fixedMockVoucher = VoucherType.FIXED_AMOUNT_VOUCHER.createVoucher(voucherId, value);
        Voucher percentMockVoucher = VoucherType.PERCENT_DISCOUNT_VOUCHER.createVoucher(voucherId, value);

        mockVouchers.add(fixedMockVoucher);
        mockVouchers.add(percentMockVoucher);

        // When
        when(voucherRepository.findAll()).thenReturn(mockVouchers);
        List<Voucher> vouchersFromService = voucherService.listVouchers();

        // Then
        assertNotNull(vouchersFromService);
        assertEquals(2, vouchersFromService.size());
    }
}
