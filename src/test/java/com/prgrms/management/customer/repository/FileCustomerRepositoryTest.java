package com.prgrms.management.customer.repository;

import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.customer.domain.CustomerType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

public class FileCustomerRepositoryTest {
    CustomerRepository customerRepository = new FileCustomerRepository();

    @Test
    void Customer_저장() {
        //given
        Customer customer = new Customer(CustomerType.NORMAL);
        //when
        Customer insert = customerRepository.insert(customer);
        //then
        Assertions.assertThat(insert).isEqualTo(customer);
    }
}