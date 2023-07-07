package com.prgrms.springbootbasic.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prgrms.springbootbasic.controller.voucher.VoucherController;
import com.prgrms.springbootbasic.domain.voucher.Voucher;
import com.prgrms.springbootbasic.enums.VoucherType;
import com.prgrms.springbootbasic.service.voucher.VoucherService;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class VoucherControllerTest {

    @Mock
    private VoucherService voucherService;

    private VoucherController voucherController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        voucherController = new VoucherController(voucherService);
    }

    @Test
    @DisplayName("바우처 목록 validation 테스트")
    void getVoucherListControllerTest() {
        // given
        Map<UUID, Voucher> voucherMap = Collections.emptyMap();
        when(voucherService.fetchAllVouchers()).thenReturn(voucherMap);

        // when
        Map<UUID, Voucher> voucherList = voucherController.printVoucherList();

        // then
        assertThat(voucherList).isEmpty();
        verify(voucherService, times(1)).fetchAllVouchers();
    }

    @Test
    @DisplayName("바우처 생성 validation 테스트")
    void getVoucherCreateControllerTest() {
        // given
        long discount = 10000;

        // when
        voucherController.createVoucher(VoucherType.FIXED, discount);

        // then
        verify(voucherService, times(1)).createVoucher(VoucherType.FIXED, discount);
    }
}


