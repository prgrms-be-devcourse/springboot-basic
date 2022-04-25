package org.programmers.devcourse.voucher.engine.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.programmers.devcourse.voucher.EmbeddedDatabaseTestModule;
import org.springframework.dao.DataAccessException;


class JdbcCustomerRepositoryTest extends EmbeddedDatabaseTestModule {

  private static final List<UUID> uuids =
      List.of(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
  private static final List<Customer> customers = List.of(
      new Customer(uuids.get(0), "kms", "lol@gmail.com", LocalDateTime.now(), LocalDateTime.now()),
      new Customer(uuids.get(1), "minsu", "minsu@gmail.com", LocalDateTime.now(), LocalDateTime.now().minusDays(4)),
      new Customer(uuids.get(2), "faker", "faker@gmail.com", LocalDateTime.now(), LocalDateTime.now().minusDays(10)));

  private static JdbcCustomerRepository repository;

  static Stream<Arguments> uuidSource() {
    return Stream.of(arguments(uuids));
  }

  static Stream<Arguments> customerSource() {
    return Stream.of(arguments(customers));
  }

  @BeforeAll
  static void setup() {
    if (!mysql.isRunning()) {
      mysql.start();
    }
    repository = new JdbcCustomerRepository(getTestDataSource());
  }

  @BeforeEach
  void cleanUpDatabase() {
    repository.deleteAll();
    customers.forEach(customer -> {
      repository.insert(customer);
    });
  }

  @ParameterizedTest
  @MethodSource("customerSource")
  @DisplayName("DB의 모든 customer들을 불러올 수 있어야 한다.")
  void can_get_all_customers_by_single_method(List<Customer> customers) {
    var queriedCustomers = repository.getAll();
    assertThat(queriedCustomers).containsExactlyInAnyOrder(customers.toArray(Customer[]::new));
  }

  @ParameterizedTest
  @MethodSource("uuidSource")
  @DisplayName("customerId와 일치하는 Customer를 가져와야 한다.")
  void get_corresponding_customer_using_customer_id(List<UUID> uuids) {
    uuids.forEach(uuid -> {
      var customer = repository.getById(uuid);
      assertThat(customer).isNotEmpty();
      assertThat(customer.get().getCustomerId()).isIn(uuids);
    });
  }

  @ParameterizedTest
  @MethodSource("customerSource")
  @DisplayName("name과 일치하는 customer를 가져올 수 있어야 한다.")
  void get_proper_customer_with_email(List<Customer> customers) {
    customers.forEach(originalCustomer -> {
      var queriedCustomer = repository.getByName(originalCustomer.getName());
      assertThat(queriedCustomer).isNotEmpty();
      assertThat(queriedCustomer.get().getName()).isEqualTo(originalCustomer.getName());
    });
  }

  @ParameterizedTest
  @MethodSource("customerSource")
  @DisplayName("email이 일치하는 customer를 가져올 수 있어야 한다.")
  void get_proper_customer_with_name(List<Customer> customers) {
    customers.forEach(originalCustomer -> {
      var queriedCustomer = repository.getByEmail(originalCustomer.getEmail());
      assertThat(queriedCustomer).isNotEmpty();
      assertThat(queriedCustomer.get().getEmail()).isEqualTo(originalCustomer.getEmail());
    });
  }

  @ParameterizedTest
  @MethodSource("customerSource")
  @DisplayName("DB에 있는 원하는 customer를 삭제할 수 있어야 한다.")
  void can_delete_customer(List<Customer> customers) {
    var customerToDelete = customers.get(0);
    repository.delete(customerToDelete);
    assertThat(repository.getById(customerToDelete.getCustomerId())).isEmpty();
    repository.insert(customerToDelete);
  }

  @ParameterizedTest
  @MethodSource("customerSource")
  @DisplayName("DB에 있는 원하는 customer를 수정할 수 있어야 한다.")
  void can_update_customer(List<Customer> customers) {
    var customerToUpdate = customers.get(0);
    var updatedCustomer = new Customer(customerToUpdate.getCustomerId(), "boogie",
        "boogie@gmail.com", null, LocalDateTime.now());
    repository.update(updatedCustomer);
    var searchedCustomer = repository.getById(updatedCustomer.getCustomerId());
    assertThat(searchedCustomer).isNotEmpty().get().isEqualTo(updatedCustomer).matches(customer -> {
      return customer.getEmail().equals(updatedCustomer.getEmail()) && customer.getName().equals(updatedCustomer.getName());
    });
  }

  @ParameterizedTest
  @MethodSource("customerSource")
  @DisplayName("transaction이 중간에 실패할 경우 rollback 되어야 한다.")
  void if_transaction_fails_repository_must_rollback(List<Customer> customers) {
    assertThatThrownBy(() ->
        repository.runTransaction(() -> {
          repository.deleteAll();
          assertThat(repository.getAll()).isEmpty();
          repository.insert(customers.get(0));
          repository.insert(customers.get(0)); // exception expected
        })).isInstanceOf(DataAccessException.class);
    assertThat(repository.getAll()).hasSameSizeAs(customers);
  }
}
