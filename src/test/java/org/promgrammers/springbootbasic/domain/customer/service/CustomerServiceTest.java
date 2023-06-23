package org.promgrammers.springbootbasic.domain.customer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.promgrammers.springbootbasic.domain.customer.dto.response.CustomerResponse;
import org.promgrammers.springbootbasic.domain.customer.dto.response.CustomersResponse;
import org.promgrammers.springbootbasic.domain.customer.model.CustomerType;
import org.promgrammers.springbootbasic.domain.customer.repository.CustomerRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomerServiceTest {

    private CustomerService customerService;
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository = new CustomerRepository();
        customerService = new CustomerService(customerRepository);
    }

    @Test
    @DisplayName("모든 고객 조회 성공")
    void successFindAllTest() throws Exception {

        //given -> when
        CustomersResponse customersResponse = customerService.findAll();
        List<CustomerResponse> customers = customersResponse.customers();

        //then
        assertNotNull(customers);
        assertThat(customers.size()).isEqualTo(3);
        assertThat(customers).allMatch(customer -> CustomerType.BLACK.equals(customer.customerType()));
    }
}