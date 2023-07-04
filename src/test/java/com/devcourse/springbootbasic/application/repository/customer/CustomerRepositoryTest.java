package com.devcourse.springbootbasic.application.repository.customer;

import com.devcourse.springbootbasic.application.domain.customer.Customer;
import com.devcourse.springbootbasic.application.io.CsvReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

class CustomerRepositoryTest {

    CustomerRepository customerRepository;

    @BeforeEach
    void init() {
        customerRepository = new CustomerRepository(new CsvReader());
        customerRepository.setFilepath("src/main/resources/customer_blacklist.csv");
    }

    @Test
    @DisplayName("블랙고객 리스트 반환 테스트")
    void testFindAll() {
        var result = customerRepository.findAll();
        assertThat(result, notNullValue());
        assertThat(result, instanceOf(List.class));
        assertThat(result.get(0), instanceOf(Customer.class));
    }

}