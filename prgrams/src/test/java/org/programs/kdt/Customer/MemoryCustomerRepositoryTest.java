package org.programs.kdt.Customer;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("dev")
class MemoryCustomerRepositoryTest {
  @Configuration
  @ComponentScan(basePackages = {"org.programs.kdt.Customer"})
  static class Config {

    @Bean
    MemoryCustomerRepository customerRepository() {
      return new MemoryCustomerRepository();
    }
  }

  @Autowired MemoryCustomerRepository customerRepository;

Customer customer;

  @BeforeAll
  void setup() {
    customer =
        new Customer(
            UUID.randomUUID(),
            "choi",
            "choi@naver.com",
            LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
  }

  @Test
  @Order(1)
  @DisplayName("고객을 추가할 수 있다.")
  public void testInsert() {

    try {
      customerRepository.insert(customer);
    } catch (BadSqlGrammarException e) {
      e.getSQLException().getErrorCode();
    }

    Optional<Customer> retrievedCustomer = customerRepository.findById(customer.getCustomerId());
    assertThat(retrievedCustomer.isEmpty(), is(false));
    assertThat(retrievedCustomer.get(), samePropertyValuesAs(customer));
  }

  @Test
  @Order(2)
  @DisplayName("전체 고객을 조회할 수 있다.")
  public void testFindAll() {
    var customers = customerRepository.findAll();
    assertThat(customers.isEmpty(), is(false));
  }

  @Test
  @Order(3)
  @DisplayName("이름으로 고객을 조회할 수 있다.")
  public void testFindByName() {
    var findCustomer = customerRepository.findByName(customer.getName());
    assertThat(findCustomer.isEmpty(), is(false));

    var unknown = customerRepository.findByName("unknown-user");
    assertThat(unknown.isEmpty(), is(true));
  }

  @Test
  @Order(4)
  @DisplayName("이메일로 고객을 조회할 수 있다.")
  public void testFindByEmail() {
    Optional<Customer> findCustomer = customerRepository.findByEmail(customer.getEmail());
    assertThat(findCustomer.isEmpty(), is(false));

    Optional<Customer> unknown = customerRepository.findByEmail("unknown-user@gmail.com");
    assertThat(unknown.isEmpty(), is(true));
  }

  @Test
  @Order(5)
  @DisplayName("고객을 수정할 수 있다.")
  public void testUpdate() {
    customer.changeName("updated-user");
    customerRepository.update(customer);

    List<Customer> all = customerRepository.findAll();
    assertThat(all, hasSize(1));
    assertThat(all, everyItem(samePropertyValuesAs(customer)));

    Optional<Customer> retrievedCustomer = customerRepository.findById(customer.getCustomerId());
    assertThat(retrievedCustomer.isEmpty(), is(false));
    assertThat(retrievedCustomer.get(), samePropertyValuesAs(customer));
  }

  @Test
  @Order(6)
  @DisplayName("고객을 블랙리스트로 등록할 수 있다.")
  public void testSetBlacklist() {
    customer.setBlacklist();
    customerRepository.update(customer);
    Optional<Customer> findCustomer = customerRepository.findById(customer.getCustomerId());
    assertThat(customer, samePropertyValuesAs(findCustomer.get()));
  }

  @Test
  @Order(7)
  @DisplayName("고객을 삭제할 수 있다.")
  public void testDeleteCustomers() {
    customerRepository.deleteAll();
    List<Customer> all = customerRepository.findAll();
    assertThat(all, hasSize(0));
  }
}
