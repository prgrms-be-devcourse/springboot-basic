package org.prgrms.vouchermanagement.voucher.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.prgrms.vouchermanagement.exception.voucher.VoucherNotFoundException;
import org.prgrms.vouchermanagement.voucher.domain.FixedAmountVoucher;
import org.prgrms.vouchermanagement.voucher.domain.PercentDiscountVoucher;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.repository.VoucherRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class VoucherListFindServiceTest {

    private final VoucherRepository voucherRepository = mock(VoucherRepository.class);
    private final VoucherListFindService voucherListFindService = new VoucherListFindService(voucherRepository);

    @Test
    @DisplayName("바우처 리스트 정상 출력 확인")
    void voucherList() {
        // given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000, UUID.randomUUID());

        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 50, UUID.randomUUID());

        voucherRepository.save(fixedAmountVoucher);
        voucherRepository.save(percentDiscountVoucher);

        when(voucherRepository.findAll())
                .thenReturn(List.of(fixedAmountVoucher, percentDiscountVoucher));

        // when
        List<Voucher> allVouchers = voucherListFindService.findAllVouchers();

        // then
        verify(voucherRepository).findAll();
        assertThat(allVouchers).hasSize(2);
        assertThat(allVouchers)
                .extracting("voucherId", "discountAmount", "voucherType")
                .contains(tuple(fixedAmountVoucher.getVoucherId(), fixedAmountVoucher.getDiscountAmount(), fixedAmountVoucher.getVoucherType()),
                        tuple(percentDiscountVoucher.getVoucherId(), percentDiscountVoucher.getDiscountAmount(), percentDiscountVoucher.getVoucherType()));
    }

    @Test
    @DisplayName("CustomerId로 VoucherList 불러오기 확인")
    void findVouchersByCustomerId() {
        // given
        UUID customerId = UUID.randomUUID();
        Customer customer = Customer.createNormalCustomer(customerId, "name", "email@google.com", LocalDateTime.now());
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000, customerId);
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 30, customerId);

        List<Voucher> vouchers = List.of(fixedAmountVoucher, percentDiscountVoucher);

        when(voucherRepository.findVouchersByCustomerId(customer.getCustomerId()))
                .thenReturn(vouchers);
        // when
        List<Voucher> result = voucherListFindService.findVouchersByCustomerId(customerId);

        // then
        verify(voucherRepository).findVouchersByCustomerId(customerId);
        assertThat(result).hasSize(2);
        assertThat(result)
                .extracting("voucherId", "discountAmount", "voucherType", "customerId")
                .contains(tuple(fixedAmountVoucher.getVoucherId(), fixedAmountVoucher.getDiscountAmount(), fixedAmountVoucher.getVoucherType(), fixedAmountVoucher.getCustomerId()))
                .contains(tuple(percentDiscountVoucher.getVoucherId(), percentDiscountVoucher.getDiscountAmount(), percentDiscountVoucher.getVoucherType(), percentDiscountVoucher.getCustomerId()));
    }

    @Test
    @DisplayName("VoucherId로 Voucher 찾기")
    void findVoucherByVoucherId() {
        // given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000, UUID.randomUUID());
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 30, UUID.randomUUID());

        when(voucherRepository.findById(fixedAmountVoucher.getVoucherId()))
                .thenReturn(Optional.of(fixedAmountVoucher));

        when(voucherRepository.findById(percentDiscountVoucher.getVoucherId()))
                .thenReturn(Optional.of(percentDiscountVoucher));

        // when
        Voucher findFixedAmountVoucher = voucherListFindService.findVoucherByVoucherId(fixedAmountVoucher.getVoucherId());
        Voucher findPercentDiscountVoucher = voucherListFindService.findVoucherByVoucherId(percentDiscountVoucher.getVoucherId());

        // then
        verify(voucherRepository).findById(fixedAmountVoucher.getVoucherId());
        verify(voucherRepository).findById(percentDiscountVoucher.getVoucherId());

        assertThat(findFixedAmountVoucher)
                .usingRecursiveComparison()
                .isEqualTo(findFixedAmountVoucher);

        assertThat(percentDiscountVoucher)
                .usingRecursiveComparison()
                .isEqualTo(findPercentDiscountVoucher);
    }

    @Test
    @DisplayName("VoucherId로 Voucher를 찾을 때 DB에 Voucher가 존재하지 않는 경우")
    void findVoucherByVoucherIdWhenVoucherNotExist() {
        // given
        UUID voucherId = UUID.randomUUID();
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000, voucherId);

        when(voucherRepository.findById(fixedAmountVoucher.getVoucherId()))
                .thenThrow(VoucherNotFoundException.class);

        // when, then
        assertThrows(VoucherNotFoundException.class, () -> voucherListFindService.findVoucherByVoucherId(voucherId));
    }
}