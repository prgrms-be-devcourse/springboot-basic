package com.prgrms.vouchermanagement.voucher.service;

import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import com.prgrms.vouchermanagement.voucher.repository.VoucherRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @Mock
    VoucherRepository voucherRepository;

    @Test
    @DisplayName("Voucher를 추가한다.")
    void addVoucherTest() {
        // given
        int amount = 50;
        VoucherService voucherService = new VoucherService(voucherRepository);

        // when
        Voucher savedVoucher = voucherService.addVoucher(VoucherType.FIXED_DISCOUNT, amount);

        // then
        verify(voucherRepository).save(any());
        Assertions.assertThat(savedVoucher.getAmount()).isEqualTo(amount);
    }

    @Test
    @DisplayName("전체 Voucher를 조회한다.")
    void findAllVouchersTest() {
        // given
        VoucherService voucherService = new VoucherService(voucherRepository);
        Voucher voucher1 = VoucherType.FIXED_DISCOUNT.constructor(UUID.randomUUID(), 5000, LocalDateTime.now());
        Voucher voucher2 = VoucherType.FIXED_DISCOUNT.constructor(UUID.randomUUID(), 5000, LocalDateTime.now());
        Voucher voucher3 = VoucherType.FIXED_DISCOUNT.constructor(UUID.randomUUID(), 5000, LocalDateTime.now());
        when(voucherRepository.findAll()).thenReturn(List.of(voucher1, voucher2, voucher3));

        // when
        List<Voucher> vouchers = voucherService.findAllVouchers();

        // then
        Assertions.assertThat(vouchers).contains(voucher1, voucher2, voucher3);
    }

}