package org.promgrammers.springbootbasic.domain.customer.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.promgrammers.springbootbasic.domain.customer.model.Customer;
import org.promgrammers.springbootbasic.domain.customer.model.CustomerType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomerRepositoryTest {
    private CustomerRepository customerRepository;

    @BeforeEach
    void beforeEach() {
        customerRepository = new CustomerRepository();
    }

    @Test
    @DisplayName("조회 성공 - 파일에 저장된 블랙리스트 조회 ")
    void successFindAllTest() throws Exception {
        //given -> when
        List<Customer> customers = customerRepository.findAll();

        //then
        assertNotNull(customers);
        assertThat(customers.size()).isEqualTo(3);
        assertThat(customers).allMatch(customer -> CustomerType.BLACK.equals(customer.customerType()));
    }

}