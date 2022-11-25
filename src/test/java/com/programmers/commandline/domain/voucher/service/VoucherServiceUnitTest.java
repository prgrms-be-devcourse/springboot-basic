package com.programmers.commandline.domain.voucher.service;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.entity.VoucherType;
import com.programmers.commandline.domain.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class VoucherServiceUnitTest {

    VoucherRepository voucherRepositoryMock = mock(VoucherRepository.class);
    VoucherService voucherService = new VoucherService(voucherRepositoryMock);

    @Test
    @DisplayName("바우처를 1개 생성하는 service객체에서 voucherRepository가 1회 실행되는지 검증하라")
    void insert() {
        //given
        UUID id = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();
        VoucherType type = VoucherType.FIXED_AMOUNT;
        long discount = 100L;

        //when
        Voucher voucher = type.createVoucher(id, discount, createdAt);
        when(voucherRepositoryMock.insert(any())).thenReturn(voucher);
        String insert = voucherService.insert(type, discount);

        //then
        verify(voucherRepositoryMock, times(1)).insert(any());
    }

    @Test
    void findAll() {
        //given
        List<Voucher> vouchers = new ArrayList<>();

        //when
        when(voucherRepositoryMock.findAll()).thenReturn(vouchers);
        String findAllVouchers = voucherService.findAll();

        //then
        verify(voucherRepositoryMock, times(1)).findAll();
        assertThat(findAllVouchers, is(""));
    }
}