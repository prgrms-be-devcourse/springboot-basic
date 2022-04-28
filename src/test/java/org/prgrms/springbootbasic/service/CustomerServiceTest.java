package org.prgrms.springbootbasic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.springbootbasic.controller.VoucherType;
import org.prgrms.springbootbasic.entity.Customer;
import org.prgrms.springbootbasic.entity.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.Voucher;
import org.prgrms.springbootbasic.exception.DuplicateCustomerEmailException;
import org.prgrms.springbootbasic.exception.InvalidCustomerIdException;
import org.prgrms.springbootbasic.exception.InvalidVoucherIdException;
import org.prgrms.springbootbasic.repository.customer.CustomerRepository;
import org.prgrms.springbootbasic.repository.voucher.VoucherRepository;


@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    VoucherRepository voucherRepository;

    CustomerService customerService;

    @BeforeEach
    void init() {
        customerService = new CustomerService(customerRepository, voucherRepository);
    }

    @DisplayName("회원 생성 테스트 - 정상")
    @Test
    void creatCustomer() {
        //given
        String email = "test@gmail.com";
        when(customerRepository.findByEmail(email)).thenReturn(Optional.empty());

        //when
        customerService.createCustomer("test", email);

        //then
        var inOrder = inOrder(customerRepository);
        inOrder.verify(customerRepository).findByEmail(email);
        inOrder.verify(customerRepository).save(any(Customer.class));
    }

    @DisplayName("회원 생성 테스트 - 이메일 중복")
    @Test
    void createCustomerException() {
        //given
        String email = "test@gmail.com";
        when(customerRepository.findByEmail(email))
            .thenReturn(Optional.of(new Customer(UUID.randomUUID(), "a", email)));

        //when
        //then
        assertThatThrownBy(() -> customerService.createCustomer("test", email))
            .isInstanceOf(DuplicateCustomerEmailException.class);
    }

    @DisplayName("모든 회원 조회 테스트")
    @Test
    void findAllCustomers() {
        //given
        var customer1 = new Customer(UUID.randomUUID(), "test", "test@gmail.com");
        var customer2 = new Customer(UUID.randomUUID(), "test01", "test01@gmail.com");

        List<Customer> customers = List.of(customer1, customer2);
        when(customerRepository.findAll()).thenReturn(customers);

        //when
        var allCustomers = customerService.findAllCustomers();

        //then
        assertThat(allCustomers)
            .containsExactlyInAnyOrder(customer1, customer2);
    }

    @DisplayName("특정 회원의 바우처 삭제하기 - 정상 케이스")
    @Test
    void deleteVoucher() {
        //given
        var customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com");
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(
            Optional.of(customer));

        var voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        when(voucherRepository.findByCustomer(customer)).thenReturn(List.of(voucher));

        //when
        customerService.deleteVoucher(customer.getCustomerId(), voucher.getVoucherId());

        //then
        var inOrder = inOrder(customerRepository, voucherRepository);
        inOrder.verify(customerRepository).findById(any(UUID.class));
        inOrder.verify(voucherRepository).findByCustomer(any(Customer.class));
        inOrder.verify(voucherRepository).deleteVoucher(any(Voucher.class));
    }

    @DisplayName("특정 회원의 바우처 삭제하기 - 유효하지 않은 손님 아이디 케이스")
    @Test
    void deleteVoucherInvalidCustomerId() {
        //given
        var customerId = UUID.randomUUID();
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(
            () -> customerService.deleteVoucher(customerId, UUID.randomUUID()))
            .isInstanceOf(InvalidCustomerIdException.class);
    }

    @DisplayName("특정 회원의 바우처 삭제하기 - 유효하지 않은 바우처 아이디 케이스")
    @Test
    void deleteVoucherInvalidVoucherId() {
        //given
        var customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com");
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(
            Optional.of(customer));
        when(voucherRepository.findByCustomer(customer)).thenReturn(Collections.emptyList());

        //when
        //then
        assertThatThrownBy(
            () -> customerService.deleteVoucher(customer.getCustomerId(), UUID.randomUUID()))
            .isInstanceOf(InvalidVoucherIdException.class);
    }

    @DisplayName("특정 바우처를 갖고 있는 회원 조회")
    @ParameterizedTest
    @EnumSource(VoucherType.class)
    void findCustomerHavingSpecificVoucherType(VoucherType voucherType) {
        //given
        //when
        customerService.findCustomerHavingSpecificVoucherType(voucherType);

        //then
        verify(customerRepository).findByVoucherType(voucherType);
    }

    @Test
    @DisplayName("아이디로 손님 찾기")
    void findCustomer() {
        //given
        var customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com");
        given(customerRepository.findById(customer.getCustomerId()))
            .willReturn(Optional.of(customer));

        //when
        var foundCustomer = customerService.findCustomer(customer.getCustomerId());

        //then
        assertThat(foundCustomer).isEqualTo(customer);
    }

    @Test
    @DisplayName("특정 아이디 손님 삭제")
    void testDeleteCustomer() {
        //given
        //when
        customerService.deleteCustomer(UUID.randomUUID());

        //then
        verify(customerRepository).deleteById(any(UUID.class));
    }
}