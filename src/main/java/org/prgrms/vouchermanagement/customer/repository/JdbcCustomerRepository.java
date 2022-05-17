package org.prgrms.vouchermanagement.customer.repository;

import org.prgrms.vouchermanagement.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

  private static final Logger log = LoggerFactory.getLogger(JdbcCustomerRepository.class);

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Customer insert(Customer customer) {
    var update = jdbcTemplate.update("INSERT INTO customers(customer_id, name, email, created_at) VALUES (UUID_TO_BIN(:customerId), :name, :email, :createdAt)",
      toParamMap(customer));
    if(update != 1) {
      throw new RuntimeException("Nothing was inserted");
    }
    return customer;
  }

  @Override
  public Customer update(Customer customer) {
    var update = jdbcTemplate.update("UPDATE customers SET name = :name, email = :email, last_login_at = :lastLoginAt WHERE customer_id = UUID_TO_BIN(:customerId)",
      toParamMap(customer));
    if(update != 1) {
      throw new RuntimeException("Nothing was updated");
    }
    return customer;
  }

  @Override
  public int count() {
    return jdbcTemplate.queryForObject("SELECT count(*) FROM customers",
      Collections.emptyMap(),Integer.class);
  }

  @Override
  public List<Customer> findAll() {
    return jdbcTemplate.query("SELECT * FROM customers", customerRowMapper);
  }

  @Override
  public boolean checkExistenceById(UUID customerId) {
    return jdbcTemplate.queryForObject("SELECT EXISTS(SELECT FROM customers WHERE email = UUID_TO_BIN(:customerId))",
      Collections.singletonMap("customerId", customerId.toString().getBytes()),
      Boolean.class
    );
  }

  @Override
  public Optional<Customer> findById(UUID customerId) {
    try {
      return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)",
        Collections.singletonMap("customerId", customerId.toString().getBytes()),
        customerRowMapper));
    } catch (EmptyResultDataAccessException e) {
      log.error("Get empty result", e);
      return Optional.empty();
    }
  }

  @Override
  public Optional<Customer> findByName(String name) {
    try {
      return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE name = :name",
        Collections.singletonMap("name", name),
        customerRowMapper));
    } catch (EmptyResultDataAccessException e) {
      log.error("Get empty result", e);
      return Optional.empty();
    }
  }

  @Override
  public Optional<Customer> findByEmail(String email) {
    try {
      return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE email = :email",
        Collections.singletonMap("email", email),
        customerRowMapper));
    } catch (EmptyResultDataAccessException e) {
      log.error("Get empty result", e);
      return Optional.empty();
    }
  }

  @Override
  public void deleteAll() {
    jdbcTemplate.update("DELETE FROM customers", Collections.emptyMap());
  }

  private void mapToCustomer(List<Customer> allCustomers, ResultSet resultSet) throws SQLException {
    var customerName = resultSet.getString("name");
    var email = resultSet.getString("email");
    var customerId = toUUID(resultSet.getBytes("customer_id"));
    var lastLoginAt = resultSet.getTimestamp("last_login_at") != null?
      resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
    var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
    log.info("customer id -> {}, customer name -> {}, createdAt -> {}", customerId, customerName, createdAt);
    allCustomers.add(new Customer(customerId, customerName, email, lastLoginAt, createdAt));
  }

  private static RowMapper<Customer> customerRowMapper =  (resultSet, i) -> {
    String customerName = resultSet.getString("name");
    String email = resultSet.getString("email");
    UUID customerId = toUUID(resultSet.getBytes("customer_id"));
    LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
      resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
    LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
    return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
  };

  private Map<String, Object> toParamMap(Customer customer) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("customerId", customer.getCustomerId().toString().getBytes());
    paramMap.put("name", customer.getName());
    paramMap.put("email", customer.getEmail().getAddress());
    paramMap.put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
    paramMap.put("lastLoginAt", customer.getLastLoginAt() != null? Timestamp.valueOf(customer.getLastLoginAt()): null);
    return paramMap;
  }

  static UUID toUUID(byte[] bytes) throws SQLException {
    var byteBuffer = ByteBuffer.wrap(bytes);
    return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
  }
}
