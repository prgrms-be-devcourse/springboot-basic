package org.prgrms.kdt.domain.voucher.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.prgrms.kdt.domain.voucher.repository.VoucherRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @Mock
    VoucherRepository voucherRepository;

    @InjectMocks
    VoucherService voucherService;

    @Test
    @DisplayName("고정할인 바우처가 정상적으로 저장된다.")
    void saveFixedAmountVoucher(){
        //given
        UUID voucherId = UUID.randomUUID();
        long discountPrice = 10000;
        VoucherType voucherType = VoucherType.FIXED_AMOUNT;
        LocalDateTime now = LocalDateTime.now();
        Voucher voucher = new Voucher(voucherId, voucherType, discountPrice, now, now);
        //when
        when(voucherRepository.save(any())).thenReturn(voucherId);
        UUID savedVoucherId = voucherService.save(voucher);
        //then
        assertThat(savedVoucherId).isEqualTo(voucherId);
    }

    @Test
    @DisplayName("정률할인 바우처가 정상적으로 저장된다.")
    void savePercentDiscountVoucher(){
        //given
        UUID voucherId = UUID.randomUUID();
        long percentDiscount = 90;
        VoucherType voucherType = VoucherType.PERCENT_DISCOUNT;
        LocalDateTime now = LocalDateTime.now();
        Voucher voucher = new Voucher(voucherId, voucherType, percentDiscount, now, now);
        //when
        when(voucherRepository.save(any())).thenReturn(voucherId);
        UUID savedVoucherId = voucherService.save(voucher);
        //then
        assertThat(savedVoucherId).isEqualTo(voucherId);
    }

    @Test
    @DisplayName("저장된 모든 바우처를 조회할 수 있다.")
    void getAllVouchers(){
        //given
        LocalDateTime now = LocalDateTime.now();
        List<Voucher> savedVouchers = Arrays.asList(
                new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 10000L, now, now),
                new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, 10L, now, now));
        //when
        when(voucherRepository.findAll()).thenReturn(savedVouchers);
        List<Voucher> vouchers = voucherService.getAllVouchers();
        //then
        assertThat(vouchers.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("id를 통해 저장된 바우처를 조회할 수 있다")
    void getVoucherById() {
        //given
        UUID fixedVoucherId = UUID.randomUUID();
        UUID percentVoucherId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        Voucher fixedAmountVoucher = new Voucher(fixedVoucherId, VoucherType.FIXED_AMOUNT,10000L, now, now);
        Voucher percentDiscountVoucher = new Voucher(percentVoucherId, VoucherType.PERCENT_DISCOUNT, 20L, now, now);
        //when
        when(voucherRepository.findById(fixedVoucherId)).thenReturn(Optional.of(fixedAmountVoucher));
        when(voucherRepository.findById(percentVoucherId)).thenReturn(Optional.of(percentDiscountVoucher));
        Optional<Voucher> findFixedVoucher = voucherService.getVoucherById(fixedVoucherId);
        Optional<Voucher> findPercentVoucher = voucherService.getVoucherById(percentVoucherId);
        //then
        assertThat(findFixedVoucher.orElse(null)).isEqualTo(fixedAmountVoucher);
        assertThat(findPercentVoucher.orElse(null)).isEqualTo(percentDiscountVoucher);
    }

    @Test
    @DisplayName("저장되어 있지 않은 바우처를 조회 시 null을 반환한다")
    void getVoucherByIdIsNull() {
        //given
        //when
        when(voucherRepository.findById(any())).thenReturn(null);
        Optional<Voucher> voucher = voucherService.getVoucherById(UUID.randomUUID());
        //then
        assertThat(voucher).isNull();
    }

    @Test
    @DisplayName("바우처 타입과 날짜를 통해 저장된 바우처를 조회할 수 있다.")
    void getVoucherByTypeAndDate(){
        //given
        LocalDateTime now = LocalDateTime.now();
        List<Voucher> savedVouchers = Arrays.asList(
                new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 10000L, now, now),
                new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, 10L, now, now));
        //when
        when(voucherRepository.findByTypeAndDate(any(), any())).thenReturn(savedVouchers);
        List<Voucher> vouchers = voucherService.getVoucherByTypeAndDate(VoucherType.FIXED_AMOUNT, now.toLocalDate());
        //then
        assertThat(vouchers.size()).isEqualTo(2);
        assertThat(vouchers).contains(savedVouchers.get(0), savedVouchers.get(1));
    }
}