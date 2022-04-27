package org.prgms.voucherProgram.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.voucherProgram.entity.voucher.FixedAmountVoucher;
import org.prgms.voucherProgram.entity.voucher.PercentDiscountVoucher;
import org.prgms.voucherProgram.entity.voucher.Voucher;
import org.prgms.voucherProgram.entity.voucher.VoucherType;
import org.prgms.voucherProgram.exception.WrongDiscountAmountException;
import org.prgms.voucherProgram.exception.WrongDiscountPercentException;
import org.prgms.voucherProgram.repository.voucher.VoucherRepository;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @Mock
    VoucherRepository voucherRepository;

    @InjectMocks
    VoucherService voucherService;

    @DisplayName("FixedAmountVoucerType을 주면 FixedAmountVoucher를 반환한다.")
    @Test
    void create_FixedAmountVoucherType_ReturnFixedAmountVoucher() throws
        WrongDiscountPercentException,
        WrongDiscountAmountException {

        Voucher mockVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        given(voucherRepository.save(any(FixedAmountVoucher.class))).willReturn(mockVoucher);

        Voucher voucher = voucherService.create(VoucherType.FIXED_AMOUNT, 10L);

        assertThat(voucher).isInstanceOf(FixedAmountVoucher.class);
        assertThat(voucher).extracting("discountAmount")
            .isEqualTo(10L);
        then(voucherRepository).should(times(1)).save(any(Voucher.class));
    }

    @DisplayName("PercentDiscountVoucerType을 주면 PercentDiscountVoucher를 반환한다.")
    @Test
    void create_PercentDiscountType_ReturnPercentDiscountVoucher() throws
        WrongDiscountPercentException,
        WrongDiscountAmountException {
        Voucher mockVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10L);
        given(voucherRepository.save(any(PercentDiscountVoucher.class))).willReturn(mockVoucher);

        Voucher voucher = voucherService.create(VoucherType.PERCENT_DISCOUNT, 10L);

        assertThat(voucher).isInstanceOf(PercentDiscountVoucher.class);
        assertThat(voucher).extracting("discountPercent")
            .isEqualTo(10L);
        then(voucherRepository).should(times(1)).save(any(Voucher.class));
    }

    @DisplayName("모든 바우처를 반환한다.")
    @Test
    void findAllVoucher_ReturnAllVoucher() throws WrongDiscountAmountException, WrongDiscountPercentException {
        List<Voucher> mockVouchers = Arrays.asList(new FixedAmountVoucher(UUID.randomUUID(), 10L),
            new PercentDiscountVoucher(UUID.randomUUID(), 20L));
        given(voucherRepository.findAll()).willReturn(mockVouchers);

        List<Voucher> vouchers = voucherService.findAllVoucher();

        assertThat(vouchers).hasSize(2)
            .isEqualTo(mockVouchers);
        then(voucherRepository).should(times(1)).findAll();
    }
}
