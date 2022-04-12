package com.prgrms.management.customer.repository;

import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.customer.domain.CustomerType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileCustomerRepositoryTest {
    CustomerRepository customerRepository = new FileCustomerRepository();

    @Test
    void NORMAL_Customer_저장() {
        //given
        Customer customer = new Customer(CustomerType.NORMAL);
        //when
        Customer insert = customerRepository.insert(customer);
        //then
        Assertions.assertThat(insert).isEqualTo(customer);
    }

    @Test
    void BLACKLIST_Customer_저장() {
        //given
        Customer customer = new Customer(CustomerType.BLACKLIST);
        //when
        Customer insert = customerRepository.insert(customer);
        //then
        Assertions.assertThat(insert).isEqualTo(customer);
    }
}