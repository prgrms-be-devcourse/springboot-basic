package com.zerozae.voucher.service;

import com.zerozae.voucher.domain.voucher.FixedDiscountVoucher;
import com.zerozae.voucher.domain.voucher.PercentDiscountVoucher;
import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.domain.voucher.VoucherType;
import com.zerozae.voucher.dto.voucher.VoucherRequest;
import com.zerozae.voucher.dto.voucher.VoucherResponse;
import com.zerozae.voucher.repository.voucher.VoucherRepository;
import com.zerozae.voucher.service.voucher.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class VoucherServiceTest {
    VoucherRepository voucherRepository = mock(VoucherRepository.class);
    VoucherService voucherService = new VoucherService(voucherRepository);

    @Test
    @DisplayName("바우처 등록 메서드 호출 테스트")
    void callCreateVoucherTest(){
        // Given
        VoucherRequest voucherRequest = new VoucherRequest(10L, VoucherType.FIXED);
        Voucher savedVoucher = new FixedDiscountVoucher(10L);

        when(voucherRepository.save(any(Voucher.class))).thenReturn(savedVoucher);

        // When
        VoucherResponse voucherResponse = voucherService.createVoucher(voucherRequest);

        // Then
        assertEquals(10L, voucherResponse.getDiscount());
        assertEquals(voucherResponse.getVoucherType(), VoucherType.FIXED);
    }

    @Test
    @DisplayName("바우처 리스트 조회 메서드 호출 테스트")
    void callFindAllVouchersTest(){
        // Given
        List<Voucher> voucherList = List.of(
                new FixedDiscountVoucher(10L),
                new PercentDiscountVoucher(20L)
        );

        when(voucherRepository.findAll()).thenReturn(voucherList);

        // When
        List<VoucherResponse> vouchers = voucherService.findAllVouchers();

        // Then
        assertEquals(vouchers.size() , 2);
        assertTrue(vouchers.stream().anyMatch(voucher -> voucher.getDiscount() == 10L));
        assertTrue(vouchers.stream().anyMatch(voucher -> voucher.getDiscount() == 20L));
    }
}
