package com.programmers.springbootbasic.voucher.controller;

import com.programmers.springbootbasic.io.Console;
import com.programmers.springbootbasic.voucher.domain.FixedAmountVoucher;
import com.programmers.springbootbasic.voucher.domain.PercentDiscountVoucher;
import com.programmers.springbootbasic.voucher.domain.Voucher;
import com.programmers.springbootbasic.voucher.domain.VoucherType;
import com.programmers.springbootbasic.voucher.dto.VoucherDto;
import com.programmers.springbootbasic.voucher.dto.VouchersResponseDto;
import com.programmers.springbootbasic.voucher.service.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

class VoucherControllerTest {

    @Mock
    private Console console;

    @Mock
    private VoucherService voucherService;

    private VoucherController voucherController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        voucherController = new VoucherController(console, voucherService);
    }

    @DisplayName("바우처 목록을 조회한다")
    @Test
    void getVoucherList() {
        //given
        List<Voucher> mockVouchers = new ArrayList<>();
        mockVouchers.add(new FixedAmountVoucher(UUID.randomUUID(), "Voucher1", 10L));
        mockVouchers.add(new PercentDiscountVoucher(UUID.randomUUID(), "Voucher2", 50L));

        when(voucherService.findAll()).thenReturn(new VouchersResponseDto(mockVouchers));

        //when
        List<Voucher> vouchers = voucherController.getVoucherList();

        //then
        assertThat(mockVouchers.size(), is(vouchers.size()));
    }

    @DisplayName("업데이트 할 바우처의 Id를 입력받고 해당하는 바우처를 찾아서 반환한다")
    @Test
    void getVoucherToUpdate() {
        //given
        UUID voucherId = UUID.randomUUID();
        VoucherDto mockVoucher = new VoucherDto(voucherId, "Voucher1", 100, VoucherType.FixedAmountVoucher, Optional.empty());

        when(voucherService.findById(voucherId)).thenReturn(mockVoucher);
        when(console.readInput()).thenReturn(voucherId.toString());

        //when
        Voucher voucher = voucherController.getVoucherToUpdate();

        //then
        assertThat(mockVoucher.id(), is(voucher.getVoucherId()));
    }
}