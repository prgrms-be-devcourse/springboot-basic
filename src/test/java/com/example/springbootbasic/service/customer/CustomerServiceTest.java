package com.example.springbootbasic.service.customer;

import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.repository.customer.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("dev")
class CustomerServiceTest {

    private final CustomerService customerService;

    public CustomerServiceTest() {
        CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);
        this.customerService = new CustomerService(customerRepository);
    }

    @RepeatedTest(100)
    @DisplayName("다수 블랙리스트 고객 검색 성공")
    void whenFindBlackCustomersThenSuccessTest() {
        // when
        List<Customer> allBlackCustomers = customerService.findAllBlackCustomers();

        // then
        assertThat(allBlackCustomers, notNullValue());
    }
}