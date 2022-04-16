package org.programmers.devcourse.voucher.engine.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.programmers.devcourse.voucher.configuration.JdbcProperties;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
class JdbcCustomerRepositoryTest {

  private static final List<UUID> uuids =
      List.of(
          UUID.randomUUID(),
          UUID.randomUUID(),
          UUID.randomUUID());
  private static final List<Customer> customers = List.of(
      new Customer(uuids.get(0), "kms", "lol@gmail.com",
          LocalDateTime.now(), LocalDateTime.now()),
      new Customer(uuids.get(1), "minsu", "minsu@gmail.com",
          LocalDateTime.now(), LocalDateTime.now().minusDays(4)),
      new Customer(uuids.get(2), "faker", "faker@gmail.com",
          LocalDateTime.now(), LocalDateTime.now().minusDays(10)));
  @Container
  public static MySQLContainer mysql = new MySQLContainer<>(
      DockerImageName
          .parse("mysql:8.0.28-debian"))
      .withDatabaseName("order_mgmt")
      .withInitScript("init.sql")
      .withUsername("test")
      .withPassword("test1234!")
      .withCommand("--character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci");


  private static JdbcCustomerRepository repository;

  static Stream<Arguments> uuidSource() {
    return Stream.of(arguments(uuids));
  }

  static Stream<Arguments> customerSource() {

    return Stream.of(arguments(customers));
  }

  @BeforeAll
  static void setup() {
    mysql.start();

    repository = new JdbcCustomerRepository(new JdbcProperties() {
      @Override
      public String getUser() {
        return mysql.getUsername();
      }

      @Override
      public String getPassword() {
        return mysql.getPassword();
      }

      @Override
      public String getUrl() {
        return mysql.getJdbcUrl();
      }
    });
    repository.deleteAll();
  }

  @ParameterizedTest
  @MethodSource("customerSource")
  @DisplayName("데이터베이스에 Customer를 넣을 수 있어야 한다.")
  @Order(1)
  void can_insert_customer_in_database(List<Customer> customers) {
    customers.forEach(customer -> {
      assertThat(repository.insert(customer)).isEqualTo(customer);
    });
  }


  @ParameterizedTest
  @MethodSource("uuidSource")
  @DisplayName("customerId와 일치하는 Customer를 가져와야 한다.")
  @Order(2)
  void get_corresponding_customer_using_customer_id(List<UUID> uuids) {

    uuids.forEach(uuid -> {
      var customer = repository.getById(uuid);
      assertThat(customer).isNotEmpty();
      assertThat(customer.get().getCustomerId()).isIn(uuids);

    });

  }


}
