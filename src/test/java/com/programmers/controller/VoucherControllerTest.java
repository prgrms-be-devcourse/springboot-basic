package com.programmers.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import com.programmers.domain.voucher.FixedAmountVoucher;
import com.programmers.domain.voucher.PercentDiscountVoucher;
import com.programmers.domain.voucher.Voucher;
import com.programmers.domain.voucher.VoucherType;
import com.programmers.domain.voucher.dto.VoucherResponseDto;
import com.programmers.domain.voucher.dto.VouchersResponseDto;
import com.programmers.io.Console;
import com.programmers.service.VoucherService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        VoucherResponseDto mockVoucher = new VoucherResponseDto(voucherId, "Voucher1", 100, VoucherType.FixedAmountVoucher);

        when(voucherService.findById(voucherId)).thenReturn(mockVoucher);
        when(console.readInput()).thenReturn(voucherId.toString());

        //when
        Voucher voucher = voucherController.getVoucherToUpdate();

        //then
        assertThat(mockVoucher.id(), is(voucher.getVoucherId()));
    }
}