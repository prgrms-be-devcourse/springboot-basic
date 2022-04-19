package org.programmers.devcourse.voucher.engine.customer;


import static org.programmers.devcourse.voucher.engine.customer.JdbcCustomerRepository.CustomerParam.CREATED_AT;
import static org.programmers.devcourse.voucher.engine.customer.JdbcCustomerRepository.CustomerParam.CUSTOMER_ID;
import static org.programmers.devcourse.voucher.engine.customer.JdbcCustomerRepository.CustomerParam.EMAIL;
import static org.programmers.devcourse.voucher.engine.customer.JdbcCustomerRepository.CustomerParam.LAST_LOGIN_AT;
import static org.programmers.devcourse.voucher.engine.customer.JdbcCustomerRepository.CustomerParam.NAME;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import org.programmers.devcourse.voucher.configuration.Transactional;
import org.programmers.devcourse.voucher.util.UUIDMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class JdbcCustomerRepository implements CustomerRepository, Transactional {

  private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  private final DataSourceTransactionManager transactionManager;

  public void runTransaction(Runnable runnable) throws DataAccessException {
    var status = transactionManager.getTransaction(null);
    try {
      runnable.run();
      transactionManager.commit(status);
    } catch (DataAccessException exception) {
      transactionManager.rollback(status);
      throw exception;
    }

  }


  private final RowMapper<Customer> mapToCustomer = (resultSet, index) -> {
    UUID customerId = null;
    try {
      customerId = UUIDMapper.fromBytes(resultSet.getBinaryStream("customer_id").readAllBytes());
    } catch (IOException e) {
      throw new SQLException("Getting UUID from binaryStream failed");
    }
    var name = resultSet.getString("name");
    var email = resultSet.getString("email");
    var lastLoginAt = resultSet.getTimestamp("last_login_at") == null ? null
        : resultSet.getTimestamp("last_login_at").toLocalDateTime();
    var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
    return new Customer(customerId, name, email, lastLoginAt, createdAt);
  };

  public JdbcCustomerRepository(DataSource dataSource) {
    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(new JdbcTemplate(dataSource));
    this.transactionManager = new DataSourceTransactionManager(dataSource);
  }


  @Override
  public Optional<Customer> getById(UUID customerId) {

    try {
      var customer = namedParameterJdbcTemplate.queryForObject(
          "SELECT customer_id, name, email, last_login_at, created_at FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)",
          Map.of("customerId", customerId.toString().getBytes(StandardCharsets.UTF_8)),
          mapToCustomer
      );
      return Optional.ofNullable(customer);
    } catch (EmptyResultDataAccessException exception) {
      return Optional.empty();

    } catch (DataAccessException exception) {

      logger.error(exception.getMessage(), exception);
      return Optional.empty();

    }

  }

  private Map<String, Object> mapToParameter(Customer customer) {
    var map = new HashMap<String, Object>();

    map.put(CUSTOMER_ID.toString(),
        customer.getCustomerId().toString().getBytes(StandardCharsets.UTF_8));
    map.put(NAME.toString(), customer.getName());
    map.put(EMAIL.toString(), customer.getEmail());
    map.put(CREATED_AT.toString(), Timestamp.valueOf(customer.getCreatedAt()));
    var lastLoginAt =
        customer.getLastLoginAt().isPresent() ? Timestamp.valueOf(customer.getLastLoginAt().get())
            : null;
    map.put(LAST_LOGIN_AT.toString(), lastLoginAt);

    return map;
  }

  @Override
  public Optional<Customer> getByName(String name) {
    try {
      var customer = namedParameterJdbcTemplate.queryForObject(
          "SELECT customer_id, name, email, last_login_at, created_at FROM customers WHERE name=:name",
          Map.of("name", name),
          mapToCustomer
      );

      return Optional.ofNullable(customer);
    } catch (DataAccessException exception) {
      logger.error("DataAccessException", exception);
      return Optional.empty();

    }
  }

  @Override
  public Optional<Customer> getByEmail(String email) {
    try {
      var customer = namedParameterJdbcTemplate.queryForObject(
          "SELECT customer_id, name, email, last_login_at, created_at FROM customers WHERE email=:email",
          Map.of("email", email),
          mapToCustomer
      );

      return Optional.ofNullable(customer);
    } catch (DataAccessException exception) {
      logger.error("DataAccessException", exception);
      return Optional.empty();

    }
  }

  @Override
  public Customer insert(Customer customer) {
    int result = namedParameterJdbcTemplate.update(
        "insert into customers(customer_id, name, email, last_login_at, created_at) values(UUID_TO_BIN(:customerId),:name,:email,:lastLoginAt,:createdAt)",
        mapToParameter(customer));
    if (result != 1) {
      return null;
    }
    return customer;
  }

  @Override
  public List<Customer> getAll() {
    try {
      var customers = namedParameterJdbcTemplate.query("SELECT * FROM customers", mapToCustomer);
      return customers;
    } catch (DataAccessException exception) {
      logger.error("DB query failed", exception);
      return Collections.emptyList();
    }
  }

  @Override
  public int deleteAll() {
    return namedParameterJdbcTemplate.update("DELETE FROM customers", Collections.emptyMap());

  }

  @Override
  public int delete(Customer customer) {
    try {
      return namedParameterJdbcTemplate.update(
          "DELETE FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)",
          Map.of(CUSTOMER_ID.toString(), UUIDMapper.toBytes(customer.getCustomerId())));


    } catch (DataAccessException exception) {
      logger.error("DB query failed", exception);
      return 0;

    }
  }

  @Override
  public Customer update(Customer customer) {

    try {
      namedParameterJdbcTemplate.update(
          "UPDATE customers SET name=:name, email=:email, created_at=:createdAt, last_login_at=:lastLoginAt WHERE customer_id = UUID_TO_BIN(:customerId)",
          mapToParameter(customer));
    } catch (DataAccessException exception) {
      logger.error("DataAccessException", exception);
    }
    return customer;
  }

  enum CustomerParam {
    CUSTOMER_ID("customerId"), NAME("name"), EMAIL("email"), LAST_LOGIN_AT(
        "lastLoginAt"), CREATED_AT("createdAt");
    private final String value;

    CustomerParam(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return value;
    }
  }


}
