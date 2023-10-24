package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.dto.customer.GetCustomersRequestDto;
import com.programmers.vouchermanagement.repository.customer.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class CustomerServiceTest {

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Test
    @DisplayName("고객 목록을 조회할 수 있다.")
    void getCustomers() {
        // given
        GetCustomersRequestDto request = new GetCustomersRequestDto();

        List<Customer> mockCustomers = Arrays.asList(
                new Customer("홍길동", true),
                new Customer("김철수", false)
        );
        given(customerRepository.findAll(any())).willReturn(mockCustomers);

        // when
        List<Customer> customers = customerService.getCustomers(request);

        // then
        assertThat(customers).hasSize(2);
        assertThat(customers).extracting(Customer::getName)
                .containsExactlyInAnyOrder("홍길동", "김철수");
        assertThat(customers).extracting(Customer::isBlacklisted)
                .containsExactlyInAnyOrder(true, false);
    }
}
