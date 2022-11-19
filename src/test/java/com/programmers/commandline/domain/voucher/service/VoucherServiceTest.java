package com.programmers.commandline.domain.voucher.service;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.entity.VoucherType;
import com.programmers.commandline.domain.voucher.entity.impl.FixedAmountVoucher;
import com.programmers.commandline.domain.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;


class VoucherServiceTest {

    @Test
    @DisplayName("입력된 바우처")
    void create() {
        //given
        VoucherType voucherType = VoucherType.FIXED_AMOUNT;
        Long discount = 100L;

        Voucher voucher = voucherType.createVoucher(UUID.randomUUID(), discount);
        VoucherRepository voucherRepositoryMock = mock(VoucherRepository.class);
        VoucherService voucherService = new VoucherService(voucherRepositoryMock);

        //when
        when(voucherRepositoryMock.save(voucher)).thenReturn(voucher.getVoucherId());
        String voucherId = voucherService.create(voucherType, discount);

        //then
        inOrder()
    }

    @Test
    void list() {
    }
}