package com.programmers.vouchermanagement.customer.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.domain.CustomerType;
import com.programmers.vouchermanagement.customer.dto.CustomerResponse;
import com.programmers.vouchermanagement.customer.dto.UpdateCustomerRequest;
import com.programmers.vouchermanagement.customer.repository.CustomerRepository;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.repository.VoucherRepository;

class CustomerServiceTest {
    CustomerRepository customerRepository;
    VoucherRepository voucherRepository;
    CustomerService customerService;

    @BeforeEach
    void setUp() {
        voucherRepository = mock(VoucherRepository.class);
        customerRepository = mock(CustomerRepository.class);
        customerService = new CustomerService(customerRepository, voucherRepository);
    }

    @Test
    @DisplayName("저장된 블랙리스트가 없을 때 블랙리스트 조회 시 빈 리스트를 반환한다.")
    void testReadBlacklistSuccessful_ReturnEmptyList() {
        //given
        doReturn(Collections.emptyList()).when(customerRepository).findBlackCustomers();

        //when
        final List<CustomerResponse> blacklist = customerService.readBlacklist();

        //then
        assertThat(blacklist.isEmpty(), is(true));

        //verify
        verify(customerRepository).findBlackCustomers();
    }

    @Test
    @DisplayName("저장된 블랙리스트 조회에 성공한다.")
    void testReadBlacklistSuccessful_ReturnList() {
        //given
        final Customer firstCustomer = new Customer(UUID.randomUUID(), "black 1", CustomerType.BLACK);
        final Customer secondCustomer = new Customer(UUID.randomUUID(), "black 2", CustomerType.BLACK);
        doReturn(List.of(firstCustomer, secondCustomer)).when(customerRepository).findBlackCustomers();

        //when
        final List<CustomerResponse> blacklist = customerService.readBlacklist();

        //then
        assertThat(blacklist, hasSize(2));

        //verify
        verify(customerRepository).findBlackCustomers();
    }

    @Test
    @DisplayName("고객 생성에 성공한다.")
    void testCustomerCreationSuccessful() {
        //given
        final String name = "test-customer";

        //when
        final CustomerResponse customerResponse = customerService.create(name);

        //then
        assertThat(customerResponse.name(), is(name));

        //verify
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    @DisplayName("이름이 너무 긴 고객 생성에 실패한다.")
    void testCustomerCreationFailed_ExcessiveNameLength() {
        //given
        final String name = "test-customer-updated-excessive-name-length";

        //when & then
        assertThatThrownBy(() -> customerService.create(name)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("저장된 고객이 없을 때 고객 전체 조회 시 빈 리스트를 반환한다.")
    void testListCustomersSuccessful_ReturnEmptyList() {
        //given
        doReturn(Collections.emptyList()).when(customerRepository).findAll();

        //when
        final List<CustomerResponse> customers = customerService.findAll();

        //then
        assertThat(customers.isEmpty(), is(true));

        //verify
        verify(customerRepository).findAll();
    }

    @Test
    @DisplayName("저장된 고객 리스트를 읽는데 성공한다.")
    void testListCustomersSuccessful_ReturnList() {
        //given
        final Customer firstCustomer = new Customer(UUID.randomUUID(), "test-customer1");
        final Customer secondCustomer = new Customer(UUID.randomUUID(), "test-customer2");
        doReturn(List.of(firstCustomer, secondCustomer)).when(customerRepository).findAll();


        //when
        final List<CustomerResponse> customers = customerService.findAll();

        //then
        assertThat(customers.isEmpty(), is(false));
        assertThat(customers, hasSize(2));

        //verify
        verify(customerRepository).findAll();
    }

    @Test
    @DisplayName("존재하지 않는 고객의 조회를 실패한다.")
    void testFindCustomerByIdFailed_NonExistentCustomer() {
        //given
        doReturn(Optional.empty()).when(customerRepository).findById(any(UUID.class));
        final UUID customerId = UUID.randomUUID();

        //when & then
        assertThatThrownBy(() -> customerService.findById(customerId)).isInstanceOf(NoSuchElementException.class);

        //verify
        verify(customerRepository).findById(customerId);
    }

    @Test
    @DisplayName("아이디로 고객 조회를 성공한다.")
    void testFindCustomerByIdSuccessful() {
        //given
        final Customer customer = new Customer(UUID.randomUUID(), "test-customer");
        doReturn(Optional.of(customer)).when(customerRepository).findById(any(UUID.class));

        //when
        CustomerResponse customerResponse = customerService.findById(customer.getCustomerId());

        //then
        assertThat(customerResponse, samePropertyValuesAs(CustomerResponse.from(customer)));

        //verify
        verify(customerRepository).findById(customer.getCustomerId());
    }

    @Test
    @DisplayName("존재하지 않는 고객의 정보 수정을 실패한다.")
    void testUpdateCustomerFailed_NonExistentCustomer() {
        //given
        doReturn(false).when(customerRepository).existById(any(UUID.class));

        //when & then
        final UpdateCustomerRequest request = new UpdateCustomerRequest(UUID.randomUUID(), "test-customer", CustomerType.NORMAL);
        assertThatThrownBy(() -> customerService.update(request)).isInstanceOf(NoSuchElementException.class);

        //verify
        verify(customerRepository).existById(request.customerId());
    }

    @Test
    @DisplayName("너무 긴 이름으로 고객 정보 수정을 실패한다.")
    void testUpdateCustomerFailed_ExcessiveNameLength() {
        //given
        final UpdateCustomerRequest request = new UpdateCustomerRequest(UUID.randomUUID(), "test-customer-updated-excessive-name-length", CustomerType.NORMAL);
        doReturn(true).when(customerRepository).existById(any(UUID.class));

        //when & then
        assertThatThrownBy(() -> customerService.update(request)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("고객 정보 수정을 성공한다.")
    void testUpdateCustomerSuccessful() {
        //given
        final UpdateCustomerRequest request = new UpdateCustomerRequest(UUID.randomUUID(), "test-customer", CustomerType.NORMAL);
        final Customer customer = new Customer(request.customerId(), request.name(), request.customerType());
        doReturn(true).when(customerRepository).existById(any(UUID.class));
        doReturn(customer).when(customerRepository).save(any(Customer.class));

        //when
        CustomerResponse customerResponse = customerService.update(request);

        //then
        assertThat(customerResponse, samePropertyValuesAs(CustomerResponse.from(customer)));

        //verify
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    @DisplayName("존재하지 않는 고객의 삭제를 실패한다.")
    void testDeleteCustomerByIdFailed_NonExistentCustomer() {
        //given
        doReturn(false).when(customerRepository).existById(any(UUID.class));
        final UUID customerId = UUID.randomUUID();

        //when & then
        assertThatThrownBy(() -> customerService.deleteById(customerId)).isInstanceOf(NoSuchElementException.class);

        //verify
        verify(customerRepository).existById(customerId);
    }

    @Test
    @DisplayName("고객 삭제를 성공한다.")
    void testDeleteCustomerByIdSuccessful() {
        //given
        doReturn(true).when(customerRepository).existById(any(UUID.class));
        final UUID customerId = UUID.randomUUID();

        //when
        customerService.deleteById(customerId);

        //verify
        verify(customerRepository).deleteById(customerId);
    }

    @Test
    @DisplayName("특정 바우처를 보유한 고객 조회를 성공한다.")
    void testFindByVoucherId() {
        //given
        final Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(10000), VoucherType.FIXED, UUID.randomUUID());
        final Customer customer = new Customer(voucher.getCustomerId(), "test-customer");
        doReturn(Optional.of(voucher)).when(voucherRepository).findById(any(UUID.class));
        doReturn(Optional.of(customer)).when(customerRepository).findById(any(UUID.class));

        //when
        CustomerResponse customerResponse = customerService.findByVoucherId(UUID.randomUUID());

        //then
        assertThat(customerResponse, samePropertyValuesAs(CustomerResponse.from(customer)));
    }
}
