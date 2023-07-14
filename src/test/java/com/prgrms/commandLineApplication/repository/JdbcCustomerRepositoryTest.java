package com.prgrms.commandLineApplication.repository;

import com.prgrms.commandLineApplication.customer.Customer;
import com.prgrms.commandLineApplication.repository.customer.JdbcCustomerRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Import(JdbcCustomerRepository.class)
@ActiveProfiles("test")
@JdbcTest
class JdbcCustomerRepositoryTest {

  private static final int EMPTY = 0;
  private static final int FIND_ALL_TEST_RESULT_SIZE = 2;

  @Autowired
  private JdbcCustomerRepository jdbcCustomerRepository;

  @Test
  @DisplayName("Id로 customer를 찾을 수 있다.")
  void findCustomerById_Success() {
    String customerName = "ImIdTest";
    String email = "testId@gmail.com";
    Customer customer = Customer.of(UUID.randomUUID(), customerName, email);
    Customer savedCustomer = jdbcCustomerRepository.save(customer);

    Customer findCustomer = jdbcCustomerRepository.findById(savedCustomer.getCustomerId());

    Assertions.assertThat(customer.getCustomerName()).isEqualTo(findCustomer.getCustomerName());
  }

  @Test
  @DisplayName("Email로 customer를 찾을 수 있다.")
  void findCustomerByEmail_Success() {
    String customerName = "ImEmailTest";
    String email = "testEmail@gmail.com";
    Customer customer = Customer.of(UUID.randomUUID(), customerName, email);
    Customer savedCustomer = jdbcCustomerRepository.save(customer);

    Customer findCustomer = jdbcCustomerRepository.findByEmail(savedCustomer.getEmail());

    Assertions.assertThat(savedCustomer.getCustomerName()).isEqualTo(findCustomer.getCustomerName());
  }

  @Test
  @DisplayName("customer의 name을 변경할 수 있다.")
  void updateCustomerName_Success() {
    String customerName = "ImUpdateTest";
    String email = "testUpdate@gmail.com";
    Customer customer = Customer.of(UUID.randomUUID(), customerName, email);
    Customer savedCustomer = jdbcCustomerRepository.save(customer);

    Customer findCustomer = jdbcCustomerRepository.findById(savedCustomer.getCustomerId());
    String newName = "ImNewTestName";
    findCustomer.updateName(newName);

    Assertions.assertThat(savedCustomer.getCustomerName()).isNotEqualTo(newName);
  }

  @Test
  @DisplayName("전체 customer를 조회할 수 있다.")
  void findAllCustomers_Success() {
    String customerName1 = "ImCustomer1";
    String customerName2 = "ImCustomer2";
    String email1 = "ImCustomer1@gmail.com";
    String email2 = "ImCustomer2@gmail.com";
    Customer customer1 = Customer.of(UUID.randomUUID(), customerName1, email1);
    Customer customer2 = Customer.of(UUID.randomUUID(), customerName2, email2);
    jdbcCustomerRepository.save(customer1);
    jdbcCustomerRepository.save(customer2);

    List<Customer> customers = jdbcCustomerRepository.findAll();
    System.out.println("customers.size() -> " + customers.size());
    Assertions.assertThat(customers.size()).isEqualTo(FIND_ALL_TEST_RESULT_SIZE);
  }

  @Test
  @DisplayName("Id로 삭제한 customer를 조회하면 예외가 발생한다.")
  void findNoExistCustomers_ThrowException() {
    String customerName = "ImDeleteTest1";
    String email = "testDelete1@gmail.com";
    Customer customer = Customer.of(UUID.randomUUID(), customerName, email);
    jdbcCustomerRepository.save(customer);

    jdbcCustomerRepository.deleteById(customer.getCustomerId());

    Assertions.assertThatThrownBy(() -> jdbcCustomerRepository.findByEmail(email))
            .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  @DisplayName("전체 customer를 삭제할 수 있다.")
  void deleteAllCustomers_Success() {
    String customerName = "ImDeleteAllTest";
    String email = "ImDeleteAllTest@gmail.com";
    Customer customer = Customer.of(UUID.randomUUID(), customerName, email);
    jdbcCustomerRepository.save(customer);

    jdbcCustomerRepository.deleteAll();

    Assertions.assertThat(jdbcCustomerRepository.findAll().size()).isEqualTo(EMPTY);
  }

}
