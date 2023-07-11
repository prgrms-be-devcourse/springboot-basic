package com.prgrms.commandLineApplication.repository;

import com.prgrms.commandLineApplication.customer.Customer;
import com.prgrms.commandLineApplication.repository.customer.JdbcCustomerRepository;
import org.junit.jupiter.api.*;
import org.assertj.core.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.NoSuchElementException;
import java.util.UUID;

@SpringBootTest
@Rollback(value = false)
class JdbcCustomerRepositoryTest {

  private static final int EMPTY = 0;

  private JdbcCustomerRepository jdbcCustomerRepository;

  @Autowired
  JdbcCustomerRepositoryTest(JdbcCustomerRepository jdbcCustomerRepository) {
    this.jdbcCustomerRepository = jdbcCustomerRepository;
  }

  @Test
  @Order(1)
  @DisplayName("Id로 customer를 찾을 수 있다.")
  void findById() {
    String customerName = "ImIdTest";
    String email = "testId@gmail.com";
    Customer customer = Customer.of(UUID.randomUUID(), customerName, email);
    Customer savedCustomer = jdbcCustomerRepository.save(customer);

    Customer findCustomer = jdbcCustomerRepository.findById(savedCustomer.getCustomerId());

    assertEquals(customer.getCustomerName(), findCustomer.getCustomerName());
  }

  @Test
  @Order(2)
  @DisplayName("Email로 customer를 찾을 수 있다.")
  void findByEmail() {
    String customerName = "ImEmailTest";
    String email = "testEmail@gmail.com";
    Customer customer = Customer.of(UUID.randomUUID(), customerName, email);
    Customer savedCustomer = jdbcCustomerRepository.save(customer);

    Customer findCustomer = jdbcCustomerRepository.findByEmail(savedCustomer.getEmail());

    assertEquals(customer.getCustomerName(), findCustomer.getCustomerName());
  }

  @Test
  @Order(3)
  @DisplayName("customer의 name을 변경할 수 있다.")
  void updateName() {
    String customerName = "ImUpdateTest";
    String email = "testUpdate@gmail.com";
    Customer customer = Customer.of(UUID.randomUUID(), customerName, email);
    Customer savedCustomer = jdbcCustomerRepository.save(customer);

    Customer findCustomer = jdbcCustomerRepository.findById(savedCustomer.getCustomerId());
    String newName = "ImNewTestName";
    findCustomer.update(newName);

    assertNotEquals(customer.getCustomerName(), newName);
  }

  @Test
  @Order(4)
  @DisplayName("Id로 삭제한 customer를 조회하면 예외가 발생한다.")
  void deleteById() {
    String customerName = "ImDeleteTest1";
    String email = "testDelete1@gmail.com";
    Customer customer = Customer.of(UUID.randomUUID(), customerName, email);
    jdbcCustomerRepository.save(customer);

    jdbcCustomerRepository.deleteById(customer.getCustomerId());

    Assertions.assertThatThrownBy(() -> jdbcCustomerRepository.findByEmail(email))
            .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  @Order(5)
  @DisplayName("전체 customer를 삭제할 수 있다.")
  void deleteAll() {
    String customerName = "ImDeleteAllTest";
    String email = "ImDeleteAllTest@gmail.com";
    Customer customer = Customer.of(UUID.randomUUID(), customerName, email);
    jdbcCustomerRepository.save(customer);

    jdbcCustomerRepository.deleteAll();

    assertEquals(jdbcCustomerRepository.findAll().size(), EMPTY);
  }

}
