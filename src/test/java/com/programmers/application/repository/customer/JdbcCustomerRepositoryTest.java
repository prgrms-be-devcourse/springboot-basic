package com.programmers.application.repository.customer;

import com.programmers.application.domain.customer.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
//@Rollback(value = false)
class JdbcCustomerRepositoryTest {

    @Autowired
    private JdbcCustomerRepository jdbcCustomerRepository;

    @DisplayName("옳바른 이름과 이메일 입력 시, save()를 실행하면 테스트가 성공한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "aCustomer, mgtmh991013@naver.com",
            "bCustomer, mgtmh991013@gmail.com"
    })
    void save(String name, String email) {
        //given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, name, email);

        //when
        jdbcCustomerRepository.save(customer);

        //then
        Customer savedCustomer = jdbcCustomerRepository.findByCustomerId(customer.getCustomerId()).get();
        assertThat(customer).usingRecursiveComparison().isEqualTo(savedCustomer);
    }

    @DisplayName("Customer 생성 및 저장 시, findByEmail()을 실행하면 해당 이메일로 Customer이 조회된다.")
    @ParameterizedTest
    @CsvSource(value = {
            "aCustomer, mgtmh991013@naver.com",
            "bCustomer, mgtmh991013@gmail.com"
    })
    void findByEmail(String name, String email) {
        //given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, name, email);
        jdbcCustomerRepository.save(customer);

        //when
        Customer savedCustomer = jdbcCustomerRepository.findByEmail(email).get();

        //then
        assertThat(customer).usingRecursiveComparison().isEqualTo(savedCustomer);
    }

    @DisplayName("Customer 생성 및 저장 시, finalAll() 실행하면 전체 Customer가 조회된다.")
    @Test
    void findAll() {
        final int expectedCount = 2;
        //given
        Customer customer1 = new Customer(UUID.randomUUID(), "aCustomer", "mgtmh991013@naver.com");
        Customer customer2 = new Customer(UUID.randomUUID(), "bCustomer", "mgtmh991013@gmail.com");
        ArrayList<Customer> customers = new ArrayList<>(List.of(customer1, customer2));
        createAndSaveCustomer(customers);

        //when
        List<Customer> customerList = jdbcCustomerRepository.findAll();

        //then
        assertThat(customerList).hasSize(expectedCount);
    }

    private void createAndSaveCustomer(List<Customer> customers) {
        for (Customer customer : customers) {
            jdbcCustomerRepository.save(customer);
        }
    }

    @DisplayName("Customer 상태를 변경하고, update() 실행하면 Customer가 변경된다.")
    @Test
    void update() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "aCustomer", "mgtmh991013@naver.com");
        jdbcCustomerRepository.save(customer);
        customer.changeName("bCustomer");
        customer.login();

        //when
        jdbcCustomerRepository.update(customer);

        //then
        Customer updatedCustomer = jdbcCustomerRepository.findByCustomerId(customer.getCustomerId()).get();
        assertThat(updatedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(updatedCustomer.getLastLoginAt()).isEqualTo(customer.getLastLoginAt());
    }
}
