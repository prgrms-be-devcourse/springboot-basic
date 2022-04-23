package org.prgrms.kdt.repository;

import static org.prgrms.kdt.utils.ByteUtils.toUUID;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdt.domain.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

  private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
    var customerId = toUUID(resultSet.getBytes("customer_id"));
    var customerName = resultSet.getString("name");
    var email = resultSet.getString("email");
    var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
    var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ? resultSet.getTimestamp(
        "last_login_at").toLocalDateTime() : null;
    return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
  };
  private static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM customer";
  private static final String SELECT_CUSTOMER_BY_VOUCHER_ID = "SELECT c.customer_id, c.name, c.email, c.last_login_at, c.created_at FROM customer c JOIN voucher v ON c.customer_id = v.customer_id WHERE voucher_id = UUID_TO_BIN(:voucher_id)";
  private static final String SELECT_CUSTOMER_BY_CUSTOMER_ID = "SELECT * FROM customer WHERE customer_id = UUID_TO_BIN(?)";
  private static final String EXISTS_CUSTOMER_BY_CUSTOMER_ID = "SELECT EXISTS(SELECT 1 FROM customer WHERE email = :email)";
  private static final String INSERT_CUSTOMER = "INSERT INTO customer (customer_id, name, email, created_at) VALUES (UUID_TO_BIN(:customerId), :name, :email, :createdAt)";
  private static final String DELETE_ALL_CUSTOMERS = "DELETE FROM customer";

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<Customer> findAll() {
    return jdbcTemplate.query(SELECT_ALL_CUSTOMERS, customerRowMapper);
  }

  @Override
  public Optional<Customer> findById(UUID customerId) {

    return jdbcTemplate.query(SELECT_CUSTOMER_BY_CUSTOMER_ID,
            Collections.singletonMap("customerId", customerId), customerRowMapper)
        .stream().findFirst();
  }

  @Override
  public boolean existsByEmail(String email) {
    return Boolean.TRUE.equals(
        jdbcTemplate.queryForObject(EXISTS_CUSTOMER_BY_CUSTOMER_ID,
            Collections.singletonMap("email", email), Boolean.class));
  }

  @Override
  public Customer save(Customer customer) {
    var updateCount = jdbcTemplate.update(INSERT_CUSTOMER, toParamMap(customer));

    if (updateCount != 1) {
      throw new RuntimeException("Failed to insert customer");
    }

    return customer;
  }

  @Override
  public void deleteAll() {
    jdbcTemplate.update(DELETE_ALL_CUSTOMERS, Collections.emptyMap());
  }

  @Override
  public Optional<Customer> findCustomerByVoucherId(UUID voucherId) {
    try {
      var customer = jdbcTemplate.queryForObject(SELECT_CUSTOMER_BY_VOUCHER_ID,
          new HashMap<>() {{
            put("voucher_id", voucherId.toString().getBytes());
          }}, customerRowMapper);
      return Optional.of(customer);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  private HashMap<String, Object> toParamMap(Customer customer) {
    return new HashMap<>() {{
      put("customerId", customer.getCustomerId().toString().getBytes());
      put("name", customer.getName());
      put("email", customer.getEmail());
      put("createdAt", Timestamp.valueOf(
          customer.getCreatedAt() == null ? LocalDateTime.now() : customer.getCreatedAt()));
      put("lastLoginAt", Timestamp.valueOf(
          customer.getLastLoginAt() == null ? LocalDateTime.now() : customer.getLastLoginAt()));
    }};
  }
}