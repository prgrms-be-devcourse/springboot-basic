package org.programs.kdt.Customer;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("db")
class JdbcCustomerRepositoryTest {
  @Configuration
  @ComponentScan(basePackages = {"org.programs.kdt.Customer"})
  static class Config {
    @Bean
    public DataSource dataSource() {
      return new EmbeddedDatabaseBuilder()
          .generateUniqueName(true)
          .setType(H2)
          .setScriptEncoding("UTF-8")
          .ignoreFailedDrops(true)
          .addScript("customers.sql")
          .build();
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
      return new JdbcTemplate(dataSource);
    }
  }

  Customer customer;

  @Autowired JdbcCustomerRepository jdbcCustomerRepository;

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
      jdbcCustomerRepository.insert(customer);
    } catch (BadSqlGrammarException e) {
      e.getSQLException().getErrorCode();
    }

    Optional<Customer> retrievedCustomer = jdbcCustomerRepository.findById(customer.getCustomerId());
    assertThat(retrievedCustomer.isEmpty(), is(false));
    assertThat(retrievedCustomer.get(), samePropertyValuesAs(customer));
  }

  @Test
  @Order(2)
  @DisplayName("전체 고객을 조회할 수 있다.")
  public void testFindAll() {
    var customers = jdbcCustomerRepository.findAll();
    assertThat(customers.isEmpty(), is(false));
  }

  @Test
  @Order(3)
  @DisplayName("이름으로 고객을 조회할 수 있다.")
  public void testFindByName() {
    var findCustomer = jdbcCustomerRepository.findByName(customer.getName());
    assertThat(findCustomer.isEmpty(), is(false));

    var unknown = jdbcCustomerRepository.findByName("unknown-user");
    assertThat(unknown.isEmpty(), is(true));
  }

  @Test
  @Order(4)
  @DisplayName("이메일로 고객을 조회할 수 있다.")
  public void testFindByEmail() {
    Optional<Customer> findCustomer = jdbcCustomerRepository.findByEmail(customer.getEmail());
    assertThat(findCustomer.isEmpty(), is(false));

    Optional<Customer> unknown = jdbcCustomerRepository.findByEmail("unknown-user@gmail.com");
    assertThat(unknown.isEmpty(), is(true));
  }

  @Test
  @Order(5)
  @DisplayName("고객을 수정할 수 있다.")
  public void testUpdate() {
    customer.changeName("updated-user");
    jdbcCustomerRepository.update(customer);

    List<Customer> all = jdbcCustomerRepository.findAll();
    assertThat(all, hasSize(1));
    assertThat(all, everyItem(samePropertyValuesAs(customer)));

    Optional<Customer> retrievedCustomer = jdbcCustomerRepository.findById(customer.getCustomerId());
    assertThat(retrievedCustomer.isEmpty(), is(false));
    assertThat(retrievedCustomer.get(), samePropertyValuesAs(customer));
  }

  @Test
  @Order(6)
  @DisplayName("고객을 블랙리스트로 등록할 수 있다.")
  public void testSetBlacklist() {
    customer.setBlacklist();
    jdbcCustomerRepository.update(customer);
    Optional<Customer> findCustomer = jdbcCustomerRepository.findById(customer.getCustomerId());
    assertThat(customer, samePropertyValuesAs(findCustomer.get()));
  }

  @Test
  @Order(7)
  @DisplayName("고객을 삭제할 수 있다.")
  public void testDeleteCustomers() {
    jdbcCustomerRepository.deleteAll();
    List<Customer> all = jdbcCustomerRepository.findAll();
    assertThat(all, hasSize(0));
  }
}
