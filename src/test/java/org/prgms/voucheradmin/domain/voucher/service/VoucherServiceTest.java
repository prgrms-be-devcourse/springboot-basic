package org.prgms.voucheradmin.domain.voucher.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType.FIXED_AMOUNT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.voucheradmin.domain.voucher.dto.VoucherInputDto;
import org.prgms.voucheradmin.domain.voucher.entity.FixedAmountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.dao.VoucherRepository;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {
    @InjectMocks
    VoucherService voucherService;

    @Mock
    VoucherRepository voucherRepository;

    @Test
    @DisplayName("바우처 생성 테스트")
    void testVoucherCreation() {
        try {
            // when
            Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
            when(voucherRepository.save(any(Voucher.class))).thenReturn(voucher);

            // given
            voucherService.createVoucher(new VoucherInputDto(FIXED_AMOUNT, 10L));

            // then
            verify(voucherRepository).save(any());
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("바우서 조회 테스트")
    void testGetVouchers() {
        try {
            when(voucherRepository.getAll()).thenReturn(new ArrayList<Voucher>());

            voucherService.getVouchers();

            verify(voucherRepository).getAll();
        }catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}