package com.zerozae.voucher.service;

import com.zerozae.voucher.domain.voucher.VoucherType;
import com.zerozae.voucher.dto.voucher.VoucherRequest;
import com.zerozae.voucher.dto.voucher.VoucherResponse;
import com.zerozae.voucher.mock.MockVoucherRepository;
import com.zerozae.voucher.repository.voucher.VoucherRepository;
import com.zerozae.voucher.service.voucher.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VoucherServiceTest {
    VoucherService voucherService;
    VoucherRepository voucherRepository;

    @BeforeEach
    void setUp(){
        voucherRepository = new MockVoucherRepository();
        voucherService = new VoucherService(voucherRepository);
    }

    @Test
    @DisplayName("바우처 등록 메서드 호출 테스트")
    void callCreateVoucherTest(){
        // Given
        VoucherRequest voucherRequest = new VoucherRequest(10L, VoucherType.FIXED);

        // When
        VoucherResponse voucher = voucherService.createVoucher(voucherRequest);

        // Then
        assertEquals(10L, voucher.getDiscount());
    }

    @Test
    @DisplayName("바우처 리스트 조회 메서드 호출 테스트")
    void callFindAllVouchersTest(){
        // Given
        VoucherRequest fixedVoucherRequest = new VoucherRequest(10L, VoucherType.FIXED);
        VoucherRequest percentVoucherRequest = new VoucherRequest(20L, VoucherType.PERCENT);

        VoucherResponse fixedVoucher = voucherService.createVoucher(fixedVoucherRequest);
        VoucherResponse percentVoucher = voucherService.createVoucher(percentVoucherRequest);

        // When
        List<VoucherResponse> vouchers = voucherService.findAllVouchers();

        // Then
        assertEquals(vouchers.size() , 2);
        assertTrue(vouchers.contains(fixedVoucher));
        assertTrue(vouchers.contains(percentVoucher));
    }
}
