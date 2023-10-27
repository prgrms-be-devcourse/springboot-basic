package com.programmers.vouchermanagement.customer.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.domain.CustomerType;
import com.programmers.vouchermanagement.customer.dto.CustomerResponse;
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
