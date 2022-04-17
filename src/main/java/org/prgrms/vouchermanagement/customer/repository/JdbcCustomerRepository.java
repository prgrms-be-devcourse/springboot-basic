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
    var update = jdbcTemplate.update("insert into customers(customer_id, name, email, created_at) values (uuid_to_bin(:customerId), :name, :email, :createdAt)",
      toParamMap(customer));
    if(update != 1) {
      throw new RuntimeException("Nothing was inserted");
    }
    return customer;
  }

  @Override
  public Customer update(Customer customer) {
    var update = jdbcTemplate.update("update customers set name = :name, email = :email, last_login_at = :lastLoginAt where customer_id = uuid_to_bin(:customerId)",
      toParamMap(customer));

    if(update != 1) {
      throw new RuntimeException("Nothing was updated");
    }

    return customer;
  }

  @Override
  public int count() {
    return jdbcTemplate.queryForObject("select count(*) from customers",
      Collections.emptyMap(),Integer.class);
  }

  @Override
  public List<Customer> findAll() {
    return jdbcTemplate.query("select * from customers", customerRowMapper);
  }

  @Override
  public Optional<Customer> findById(UUID customerId) {
    try {
      return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers WHERE customer_id = uuid_to_bin(:customerId)",
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
      return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers WHERE name = :name",
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
      return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers WHERE email = :email",
        Collections.singletonMap("email", email),
        customerRowMapper));
    } catch (EmptyResultDataAccessException e) {
      log.error("Get empty result", e);
      return Optional.empty();
    }
  }

  @Override
  public void deleteAll() {
    jdbcTemplate.update("delete from customers", Collections.emptyMap());
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
    return new HashMap<String, Object>(){{
      put("customerId", customer.getCustomerId().toString().getBytes());
      put("name", customer.getName());
      put("email", customer.getEmail());
      put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
      put("lastLoginAt", customer.getLastLoginAt() != null? Timestamp.valueOf(customer.getLastLoginAt()): null);
    }};
  }

  static UUID toUUID(byte[] bytes) throws SQLException {
    var byteBuffer = ByteBuffer.wrap(bytes);
    return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
  }
}
