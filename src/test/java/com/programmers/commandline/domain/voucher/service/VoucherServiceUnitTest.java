package com.programmers.commandline.domain.voucher.service;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.entity.VoucherType;
import com.programmers.commandline.domain.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherServiceUnitTest {

    private VoucherRepository voucherRepositoryMock = mock(VoucherRepository.class);
    private VoucherService voucherService = new VoucherService(voucherRepositoryMock);

    @Test
    @DisplayName("바우처를 1개 생성하는 service객체에서 voucherRepository가 1회 실행되는지 검증하라")
    void insert() {
        //given
        VoucherType type = VoucherType.FIXED_AMOUNT;
        Long discount = 100L;
        Voucher voucher = type.createVoucher(UUID.randomUUID(), discount, LocalDateTime.now());

        //when
        when(voucherRepositoryMock.insert(any())).thenReturn(voucher);
        String insert = voucherService.insert(String.valueOf(type), discount);

        //then
        verify(voucherRepositoryMock, times(1)).insert(any());
    }

    @Test
    @DisplayName("바우처를 list단위로 생성하고 insert합니다. 이후 findAll을 통해서 모든 바우처를 list로 받아서 생성 list 와 foundVouchers의 값을 비교 검증 하라")
    void findAll() {
        //given
        List<Voucher> vouchers = new ArrayList<>();
        Voucher voucher = VoucherType.PERCENT_DISCOUNT.createVoucher(UUID.randomUUID(), 100L, LocalDateTime.now().withNano(0));

        vouchers.add(voucher);
        //when
        when(voucherRepositoryMock.findAll()).thenReturn(vouchers);
        List<Voucher> foundVouchers = voucherService.findAll();

        //then
        verify(voucherRepositoryMock, times(1)).findAll();
        assertThat(foundVouchers, samePropertyValuesAs(vouchers));
    }
}