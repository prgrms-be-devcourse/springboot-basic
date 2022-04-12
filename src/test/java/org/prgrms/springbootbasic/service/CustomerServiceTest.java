package org.prgrms.springbootbasic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.entity.Customer;
import org.prgrms.springbootbasic.repository.customer.JdbcCustomerRepository;

class CustomerServiceTest {

    JdbcCustomerRepository jdbcCustomerRepository = new JdbcCustomerRepository();
    CustomerService customerService = new CustomerService(jdbcCustomerRepository);

    @AfterEach
    void clean() {
        jdbcCustomerRepository.removeAll();
    }

    @DisplayName("회원 생성 테스트 - 정상")
    @Test
    void creatCustomer() {
        //given
        //when
        var customerId = customerService.createCustomer("test", "test@gmail.com");

        //then
        assertThat(jdbcCustomerRepository.findAll().get(0).getCustomerId())
            .isEqualTo(customerId);
    }

    @DisplayName("회원 생성 테스트 - 이메일 중복")
    @Test
    void createCustomerException() {
        //given
        String email = "test@gmail.com";
        var customerId = jdbcCustomerRepository.save(
            new Customer(UUID.randomUUID(), "test", email));

        //when
        //then
        assertThatThrownBy(() -> customerService.createCustomer("test01", email))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("모든 회원 조회 테스트")
    @Test
    void findAllCustomers() {
        //given
        var customer1 = new Customer(UUID.randomUUID(), "test1", "test1@gmail.com");
        var customer2 = new Customer(UUID.randomUUID(), "test2", "test2@gmail.com");
        jdbcCustomerRepository.save(customer1);
        jdbcCustomerRepository.save(customer2);

        //when
        var customers = customerService.findAllCustomers();

        //then
        assertThat(customers)
            .extracting(Customer::getCustomerId)
            .containsExactlyInAnyOrder(customer1.getCustomerId(), customer2.getCustomerId());
    }
}