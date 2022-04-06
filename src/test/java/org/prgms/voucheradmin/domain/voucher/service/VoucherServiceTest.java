package org.prgms.voucheradmin.domain.voucher.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherTypes.FIXED_AMOUNT;
import static org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherTypes.PERCENTAGE_DISCOUNT;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.voucheradmin.domain.voucher.dto.VoucherInputDto;
import org.prgms.voucheradmin.domain.voucher.entity.FixedAmountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.PercentageDiscountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.repository.VoucherRepository;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {
    @InjectMocks
    VoucherService voucherService;

    @Mock
    VoucherRepository voucherRepository;

    @Test
    void 고정_바우처_테스트() {
        VoucherInputDto voucherInputDto = new VoucherInputDto(FIXED_AMOUNT, 10L);
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);

        // when
        when(voucherRepository.save(any())).thenReturn(voucher);

        // given
        Voucher savedVoucher = voucherService.createVoucher(voucherInputDto);

        // then
        assertThat(savedVoucher.discount(120L)).isEqualTo(110L);
    }

    @Test
    void 퍼센트_바우처_테스트() {
        VoucherInputDto voucherInputDto = new VoucherInputDto(PERCENTAGE_DISCOUNT, 10L);
        Voucher voucher = new PercentageDiscountVoucher(UUID.randomUUID(), 10L);

        // when
        when(voucherRepository.save(any())).thenReturn(voucher);

        // given
        Voucher savedVoucher = voucherService.createVoucher(voucherInputDto);

        // then
        assertThat(savedVoucher.discount(100L)).isEqualTo(90L);
    }
}