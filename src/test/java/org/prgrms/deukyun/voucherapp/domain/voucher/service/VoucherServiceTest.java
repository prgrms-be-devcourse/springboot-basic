package org.prgrms.deukyun.voucherapp.domain.voucher.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.Voucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.VoucherRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.prgrms.deukyun.voucherapp.domain.testutil.Fixture.*;

class VoucherServiceTest {

    VoucherService voucherService;
    VoucherRepository mockVoucherRepository;

    @BeforeEach
    void setUp() {
        mockVoucherRepository = mock(VoucherRepository.class);
        voucherService = new VoucherService(mockVoucherRepository);
    }

    @Test
    void 성공_전체조회(){
        //given
        List<Voucher> vouchers = List.of(fixedAmountDiscountVoucher(), percentDiscountVoucher());
        when(mockVoucherRepository.findAll()).thenReturn(vouchers);

        //when
        List<Voucher> foundVouchers = voucherService.findAll();

        //then
        assertThat(foundVouchers).hasSameElementsAs(vouchers);
    }

    @Test
    void 성공_삽입(){
        //given
        Voucher voucher = fixedAmountDiscountVoucher();

        //when
        voucherService.insert(voucher);

        //then
        verify(mockVoucherRepository).insert(voucher);
    }
}