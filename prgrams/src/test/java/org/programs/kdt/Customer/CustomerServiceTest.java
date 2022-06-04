package org.programs.kdt.Customer;

import org.junit.jupiter.api.*;
import org.programs.kdt.Exception.DuplicationException;
import org.programs.kdt.Exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceTest {
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

    @Bean
    JdbcCustomerRepository jdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
      return new JdbcCustomerRepository(jdbcTemplate);
    }
  }

  @Autowired CustomerService customerService;

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
  @DisplayName("customer를 저장할 수 있다.")
  @Order(1)
  public void testSave() {
    customerService.save(customer);
    Optional<Customer> retrieveCustomer = customerService.findById(customer.getCustomerId());

    assertThat(retrieveCustomer.isPresent(), is(true));
    assertThat(retrieveCustomer.get(), samePropertyValuesAs(customer));
  }

  @Test
  @DisplayName("customer에 중복된 이메일값을 가질 수 없다.")
  @Order(2)
  public void testSaveDuplicationEmail() {
    Customer anotherCustomer =
        new Customer(
            UUID.randomUUID(),
            "duplicatio",
            "choi@naver.com",
            LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));

      assertThrows(DuplicationException.class, () -> customerService.save(anotherCustomer));
  }

  @Test
  @DisplayName("customer를 조회할 수 있다.")
  @Order(3)
  public void testFindAll() {
    List<Customer> retrieveCustomerList = customerService.findAll();
    assertThat(retrieveCustomerList, hasSize(1));
    assertThat(retrieveCustomerList, hasItem(samePropertyValuesAs(customer)));
  }

  @Test
  @DisplayName("블랙리스트만 조회할 수 있다.")
  @Order(3)
  public void findBlacklist() {
    Customer blacklist =
        new Customer(
            UUID.randomUUID(),
            "blacklist",
            "blacklist@naver.com",
            LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    blacklist.setBlacklist();
    customerService.save(blacklist);
    List<Customer> allBlackList = customerService.findAllBlackList();

    assertThat(allBlackList, hasSize(1));
    assertThat(allBlackList, hasItem(samePropertyValuesAs(blacklist)));
  }

  @Test
  @DisplayName("없는 이메일로 조회시 빈리스트 발생")
  @Order(4)
  public void findByEmailError() {
    Customer emailTest =
        new Customer(
            UUID.randomUUID(),
            "test",
            "test@naver.com",
            LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));

    assertThat(
            customerService.findByEmail(emailTest.getEmail()).isEmpty() , is(true));
  }

  @Test
  @DisplayName("없는 이메일로 삭제시 예외 발생")
  @Order(5)
  public void deleteByEmailError() {
    Customer emailTest =
        new Customer(
            UUID.randomUUID(),
            "test",
            "test@naver.com",
            LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    assertThrows(
        EntityNotFoundException.class, () -> customerService.deleteByEmail(emailTest.getEmail()));
  }

  @Test
  @DisplayName("없는 id로 조회시 예외 발생")
  @Order(6)
  public void findByIdError() {
    Customer idTest =
        new Customer(
            UUID.randomUUID(),
            "test",
            "test@naver.com",
            LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    assertThat(
        customerService.findById(idTest.getCustomerId()).isEmpty(), is(true));
  }

  @Test
  @DisplayName("없는 id로 업데이트시 예외 발생")
  @Order(6)
  public void updateByIdError() {
    Customer updateTest =
        new Customer(
            UUID.randomUUID(),
            "test",
            "test@naver.com",
            LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    assertThrows(
        EntityNotFoundException.class, () -> customerService.update(updateTest));
  }
}
