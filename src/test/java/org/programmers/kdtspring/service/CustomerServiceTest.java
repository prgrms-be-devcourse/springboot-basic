package org.programmers.kdtspring.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.kdtspring.dto.CustomerDTO;
import org.programmers.kdtspring.entity.user.Customer;
import org.programmers.kdtspring.entity.user.Email;
import org.programmers.kdtspring.entity.user.Name;
import org.programmers.kdtspring.repository.user.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @AfterEach
    void clean() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 생성")
    void testCreateCustomer() {
        CustomerDTO customerDTO =
                new CustomerDTO(UUID.randomUUID(), new Name("김지웅"), new Email("iop1996@gmail.com"),
                        LocalDateTime.now());

        customerService.createCustomer(customerDTO.email().getEmail(), customerDTO.name().getName());

        assertThat(customerRepository.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("회원 전체 조회")
    void testGetAllCustomer() {
        Customer customer = new Customer(UUID.randomUUID(), "jiwoong", "iop1996@naver.com", LocalDateTime.now());
        customerRepository.insert(customer);

        List<Customer> response = customerService.getAllCustomers();

        assertAll(
                () -> assertThat(response).hasSize(1),
                () -> assertThat(response.get(0).getCustomerId()).isEqualTo(customer.getCustomerId()),
                () -> assertThat(response.get(0).getEmail().getEmail()).isEqualTo(customer.getEmail().getEmail()),
                () -> assertThat(response.get(0).getName().getName()).isEqualTo(customer.getName().getName()),
                () -> assertThat(response.get(0).getCreatedAt()).isEqualTo(customer.getCreatedAt())
        );
    }

    @Test
    @DisplayName("회원 ID로 조회")
    void testGetCustomerById() {
        Customer customer = new Customer(UUID.randomUUID(), "jiwoong", "iop1996@naver.com", LocalDateTime.now());
        customerRepository.insert(customer);

        Customer responseCustomer = customerService.getCustomerById(customer.getCustomerId()).get();

        assertAll(
                () -> assertThat(responseCustomer.getCustomerId()).isEqualTo(customer.getCustomerId()),
                () -> assertThat(responseCustomer.getEmail().getEmail()).isEqualTo(customer.getEmail().getEmail()),
                () -> assertThat(responseCustomer.getName().getName()).isEqualTo(customer.getName().getName()),
                () -> assertThat(responseCustomer.getCreatedAt()).isEqualTo(customer.getCreatedAt())
        );
    }

    @Test
    @DisplayName("회원 이메일로 조회")
    void testGetCustomerByEmail() {
        Customer customer = new Customer(UUID.randomUUID(), "jiwoong", "iop1996@naver.com", LocalDateTime.now());
        customerRepository.insert(customer);

        Customer responseCustomer = customerService.getCustomerById(customer.getCustomerId()).get();

        assertAll(
                () -> assertThat(responseCustomer.getCustomerId()).isEqualTo(customer.getCustomerId()),
                () -> assertThat(responseCustomer.getEmail().getEmail()).isEqualTo(customer.getEmail().getEmail()),
                () -> assertThat(responseCustomer.getName().getName()).isEqualTo(customer.getName().getName()),
                () -> assertThat(responseCustomer.getCreatedAt()).isEqualTo(customer.getCreatedAt())
        );
    }
}