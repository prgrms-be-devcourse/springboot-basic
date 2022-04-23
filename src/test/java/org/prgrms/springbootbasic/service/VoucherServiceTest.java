package org.prgrms.springbootbasic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.springbootbasic.controller.VoucherType;
import org.prgrms.springbootbasic.entity.customer.Customer;
import org.prgrms.springbootbasic.entity.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.PercentDiscountVoucher;
import org.prgrms.springbootbasic.entity.voucher.Voucher;
import org.prgrms.springbootbasic.exception.AlreadyAssignedVoucherException;
import org.prgrms.springbootbasic.exception.InvalidCustomerIdException;
import org.prgrms.springbootbasic.exception.InvalidVoucherIdException;
import org.prgrms.springbootbasic.exception.ServiceException;
import org.prgrms.springbootbasic.repository.customer.CustomerRepository;
import org.prgrms.springbootbasic.repository.voucher.VoucherRepository;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @Mock
    VoucherRepository voucherRepository;

    @Mock
    CustomerRepository customerRepository;

    VoucherService voucherService;

    @BeforeEach
    void init() {
        voucherService = new VoucherService(voucherRepository, customerRepository);
    }

    @DisplayName("FixedAmountVoucher 만들기 테스트")
    @Test
    void createFixedAmountVoucher() {
        //given
        int amount = 10;

        //when
        voucherService.createVoucher(VoucherType.FIXED, amount, 0);

        //then
        verify(voucherRepository).save(any(FixedAmountVoucher.class));
    }

    @DisplayName("FixedAmountVoucher 만들기 실패")
    @ParameterizedTest
    @ValueSource(ints = {0, -1, 100000})
    void createFixedAmountVoucherFail(int amount) {
        //given
        //when
        //then
        assertThatThrownBy(() -> voucherService.createVoucher(VoucherType.FIXED, amount, 0))
            .isInstanceOf(ServiceException.class);
    }

    @DisplayName("PercentAmountVoucher 만들기 테스트")
    @Test
    void createPercentAmountVoucher() {
        //given
        //when
        voucherService.createVoucher(VoucherType.PERCENT, 0, 20);

        //then
        verify(voucherRepository).save(any(PercentDiscountVoucher.class));
    }

    @DisplayName("PercentAmountVoucher 만들기 실패")
    @ParameterizedTest
    @ValueSource(ints = {0, 101})
    void createPercentAmountVoucherFail(int percent) {
        //given
        //when
        //then
        assertThatThrownBy(() -> voucherService.createVoucher(VoucherType.PERCENT, 0, percent))
            .isInstanceOf(ServiceException.class);
    }

    @DisplayName("Voucher 전체 조회 테스트")
    @Test
    void findAll() {
        //given
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        var percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 20);
        List<Voucher> vouchers = List.of(fixedAmountVoucher, percentDiscountVoucher);

        when(voucherRepository.findAll()).thenReturn(vouchers);

        //when
        var voucherList = voucherService.findAll();

        //then
        assertThat(voucherList)
            .containsExactlyInAnyOrder(fixedAmountVoucher, percentDiscountVoucher);
    }

    @DisplayName("voucher를 customer에게 할당 기능 - 정상 상황")
    @Test
    void assignVoucherToCustomer() {
        //given
        var voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        var customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com");

        when(voucherRepository.findById(voucher.getVoucherId())).thenReturn(Optional.of(voucher));
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(
            Optional.of(customer));

        //when
        voucherService.assignVoucherToCustomer(voucher.getVoucherId(), customer.getCustomerId());

        //then
        var inOrder = inOrder(voucherRepository, customerRepository);
        inOrder.verify(voucherRepository).findById(voucher.getVoucherId());
        inOrder.verify(customerRepository).findById(customer.getCustomerId());
        inOrder.verify(voucherRepository).updateCustomerId(voucher);
    }

    @DisplayName("voucher를 customer에게 할당 기능 - 존재하지 않는 바우처")
    @Test
    void assignVoucherToCustomerInvalidVoucher() {
        //given
        UUID voucherId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        when(voucherRepository.findById(voucherId)).thenReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> voucherService.assignVoucherToCustomer(voucherId, customerId))
            .isInstanceOf(InvalidVoucherIdException.class);
    }

    @DisplayName("voucher를 customer에게 할당 기능 - 존재하지 않는 손님")
    @Test
    void assignVoucherToCustomerInvalidCustomer() {
        //given
        var voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        when(voucherRepository.findById(voucher.getVoucherId())).thenReturn(Optional.of(voucher));

        UUID customerId = UUID.randomUUID();
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(
            () -> voucherService.assignVoucherToCustomer(voucher.getVoucherId(), customerId))
            .isInstanceOf(InvalidCustomerIdException.class);
    }

    @DisplayName("voucher를 customer에게 할당 기능 - 이미 할당된 바우쳐")
    @Test
    void assignVoucherToCustomerAlreadyAssignedVoucher() {
        //given
        var voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        var customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com");
        voucher.assignCustomer(customer);

        when(voucherRepository.findById(voucher.getVoucherId())).thenReturn(Optional.of(voucher));

        //when
        //then
        assertThatThrownBy(
            () -> voucherService.assignVoucherToCustomer(voucher.getVoucherId(),
                customer.getCustomerId()))
            .isInstanceOf(AlreadyAssignedVoucherException.class);
    }

    @DisplayName("특정 customer voucher 조회 - 정상 케이스")
    @Test
    void findCustomerVoucher() {
        //given
        var customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com");
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(
            Optional.of(customer));

        //when
        voucherService.findCustomerVoucher(customer.getCustomerId());

        //then
        var inOrder = inOrder(customerRepository, voucherRepository);
        inOrder.verify(customerRepository).findById(customer.getCustomerId());
        inOrder.verify(voucherRepository).findByCustomer(customer);
    }

    @DisplayName("특정 customer voucher 조회 - 존재하지 않는 customer id 케이스")
    @Test
    void findCustomerVoucherInvalidCustomerId() {
        //given
        UUID customerId = UUID.randomUUID();
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> voucherService.findCustomerVoucher(customerId))
            .isInstanceOf(InvalidCustomerIdException.class);
    }
}
