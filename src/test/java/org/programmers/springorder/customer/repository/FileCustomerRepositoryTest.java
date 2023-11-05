package org.programmers.springorder.customer.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springorder.config.file.FileConfig;
import org.programmers.springorder.customer.model.Customer;
import org.programmers.springorder.customer.model.CustomerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
class FileCustomerRepositoryTest {


    @Configuration
    @ComponentScan(basePackageClasses = FileConfig.class)
    static class Config{
    }

    @Autowired
    CustomerRepository customerRepository;

    @AfterEach
    void clear(){
        FileCustomerRepository fileCustomerRepository = (FileCustomerRepository) customerRepository;
        fileCustomerRepository.clear();
    }


    @Test
    @DisplayName("회원 저장에 성공한다.")
    void save() {
        // given
        int currentSize = customerRepository.findAll().size();
        Customer customer1 = Customer.toCustomer(UUID.randomUUID(), "test1", CustomerType.NORMAL);
        Customer customer2 = Customer.toCustomer(UUID.randomUUID(), "test2", CustomerType.NORMAL);
        Customer customer3 = Customer.toCustomer(UUID.randomUUID(), "test3", CustomerType.BLACK);

        // when
        Customer insertedCustomer1 = customerRepository.insert(customer1);
        Customer insertedCustomer2 = customerRepository.insert(customer2);
        Customer insertedCustomer3 = customerRepository.insert(customer3);

        // then
        List<Customer> insertedCustomers = Arrays.asList(insertedCustomer1, insertedCustomer2, insertedCustomer3);
        List<Customer> all = customerRepository.findAll();

        assertThat(all).hasSize(currentSize + 3);
        assertThat(all).containsAll(insertedCustomers);

    }

    @Test
    @DisplayName("전체 회원 조회에 성공한다.")
    void findAll() {
        // given
        Customer customer1 = Customer.toCustomer(UUID.randomUUID(), "test1", CustomerType.NORMAL);
        Customer customer2 = Customer.toCustomer(UUID.randomUUID(), "test2", CustomerType.NORMAL);
        customerRepository.insert(customer1);
        customerRepository.insert(customer2);

        // when
        List<Customer> customerList = customerRepository.findAll();

        // then
        assertThat(customerList).hasSize(2);
    }

    @Test
    @DisplayName("블랙 리스트 조회 테스트")
    void findAllBlackList() {
        // given
        Customer customer1 = Customer.toCustomer(UUID.randomUUID(), "test1", CustomerType.NORMAL);
        Customer customer2 = Customer.toCustomer(UUID.randomUUID(), "test2", CustomerType.BLACK);
        customerRepository.insert(customer1);
        customerRepository.insert(customer2);

        // when
        List<Customer> findall = customerRepository.findAll();
        List<Customer> customerList = customerRepository.findAllBlackList();

        // then
        assertThat(customerList).hasSize(1);
        assertThat(customerList).contains(customer2);
    }

}