package org.prgrms.deukyun.voucherapp.util.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.deukyun.voucherapp.domain.customer.domain.Customer;
import org.prgrms.deukyun.voucherapp.domain.customer.service.CustomerService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.prgrms.deukyun.voucherapp.domain.testutil.Fixture.customer;

class CustomerSelectorTest {

    CustomerSelector customerSelector;
    CustomerService mockCustomerService;

    @BeforeEach
    void setUp() {
        mockCustomerService = mock(CustomerService.class);
        customerSelector = new CustomerSelector(mockCustomerService);
    }

    @Test
    void 성공_램덤한_사용자_선택_후_홀더에_담기() {
        //given
        List<Customer> customers = List.of(customer(), customer(), customer(), customer(), customer());
        when(mockCustomerService.findAll()).thenReturn(customers);

        //when
        customerSelector.setRandomCustomer();

        //then
        assertThat(CustomerHolder.getCustomer()).isIn(customers);
    }
}