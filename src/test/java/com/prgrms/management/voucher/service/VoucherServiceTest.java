package com.prgrms.management.voucher.service;

import com.prgrms.management.voucher.domain.FixedAmountVoucher;
import com.prgrms.management.voucher.domain.PercentAmountVoucher;
import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.repository.MemoryVoucherRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {
    @Mock
    MemoryVoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();

    @InjectMocks
    VoucherService voucherService;

    @Test
    void 바우처_리스트_조회() {
        //given
        FixedAmountVoucher voucher = new FixedAmountVoucher(1000);
        PercentAmountVoucher voucherTwo = new PercentAmountVoucher(10);
        String voucherList = Arrays.asList(voucher, voucherTwo).toString();
        //즉시 인스턴스 객체 반환
        when(voucherService.findAll()).thenReturn(voucherList);
        //when
        String vouchers = voucherService.findAll();
        //then
        Assertions.assertThat(vouchers).isEqualTo(voucherService.findAll());
    }

    @Test
    void 비어있는_리스트_조회() {
        //given
        List<Voucher> voucherList = new ArrayList<>();
        //즉시 인스턴스 객체 반환
        when(memoryVoucherRepository.findAll().orElse(null)).thenReturn(voucherList);
        //when
        String vouchers = voucherService.findAll();
        //then
        Assertions.assertThat(vouchers).isEqualTo(voucherService.findAll());
    }
}