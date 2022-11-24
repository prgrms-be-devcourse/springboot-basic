package org.prgrms.kdt.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.domain.VoucherType;
import org.prgrms.kdt.repository.VoucherRepository;
import org.prgrms.kdt.service.dto.CreateVoucherDto;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VoucherServiceTest {

    private VoucherService voucherService;
    private VoucherRepository voucherRepository;

    @BeforeEach
    void setUp() {
        this.voucherRepository = mock(VoucherRepository.class);
        this.voucherService = new VoucherService(voucherRepository);
    }

    @Test
    @DisplayName("[성공] 바우처 저장하기")
    void createVoucherTest() {
        VoucherType voucherType = VoucherType.PERCENT_DISCOUNT_VOUCHER;
        double discountAmount = 10;
        Voucher voucher = new Voucher(1L, voucherType, discountAmount);
        when(voucherRepository.saveVoucher(any())).thenReturn(Optional.of(voucher));

        CreateVoucherDto dto = new CreateVoucherDto(voucherType, discountAmount);
        boolean result = voucherService.createVoucher(dto);

        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("[실패] 바우처 저장 실패한 경우")
    void createVoucherTest_fail() {
        when(voucherRepository.saveVoucher(any())).thenReturn(Optional.empty());

        VoucherType voucherType = VoucherType.PERCENT_DISCOUNT_VOUCHER;
        double discountAmount = 10;
        CreateVoucherDto dto = new CreateVoucherDto(voucherType, discountAmount);

        boolean result = voucherService.createVoucher(dto);

        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("[성공] 모든 바우처 가져오기")
    void getAllVoucherTest() {
        VoucherType voucherType = VoucherType.PERCENT_DISCOUNT_VOUCHER;
        double discountAmount = 10;
        Voucher voucher = new Voucher(voucherType, discountAmount);

        when(voucherRepository.getAllVouchers()).thenReturn(List.of(voucher));

        List<Voucher> allVouchers = voucherService.getAllVouchers();

        Assertions.assertEquals(allVouchers.size(), 1);
    }
}
