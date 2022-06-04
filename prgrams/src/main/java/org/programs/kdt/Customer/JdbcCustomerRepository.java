package org.programs.kdt.Customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("db")
public class JdbcCustomerRepository implements CustomerRepository {

  private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);

  private final JdbcTemplate jdbcTemplate;

  private static final RowMapper<Customer> customerRowMapper =
      (resultSet, i) -> {
        var customerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        var customerId = UUID.fromString(resultSet.getString("customer_id"));
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var customerType = CustomerType.find(resultSet.getString("type"));
        return new Customer(customerId, customerName, email, createdAt, customerType);
      };

  public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Customer insert(Customer customer) {
    var update =
        jdbcTemplate.update(
            "INSERT INTO customers(customer_id, name, email, created_at, type) VALUES (?, ?, ?, ?, ?)",
            customer.getCustomerId().toString(),
            customer.getName(),
            customer.getEmail(),
            Timestamp.valueOf(customer.getCreatedAt()),
            customer.getCustomerType().getType());
    if (update != 1) {
      throw new RuntimeException("Noting was inserted");
    }
    return customer;
  }

  @Override
  public Customer update(Customer customer) {
    var update =
        jdbcTemplate.update(
            "UPDATE customers SET name = ?, email = ?, type = ? WHERE customer_id = ?",
            customer.getName(),
            customer.getEmail(),
            customer.getCustomerType().getType(),
            customer.getCustomerId().toString());
    if (update != 1) {
      throw new RuntimeException("Noting was updated");
    }
    return customer;
  }

  @Override
  public int count() {
    return jdbcTemplate.queryForObject("select count(*) from customers", Integer.class);
  }

  @Override
  public List<Customer> findAll() {
    return jdbcTemplate.query("select * from customers", customerRowMapper);
  }

  @Override
  public Optional<Customer> findById(UUID customerId) {
    try {
      return Optional.ofNullable(
          jdbcTemplate.queryForObject(
              "select * from customers WHERE customer_id = ?",
              customerRowMapper,
              customerId.toString()));
    } catch (EmptyResultDataAccessException e) {
      logger.error("Got empty result", e);
      return Optional.empty();
    }
  }

  @Override
  public List<Customer> findByName(String name) {
    return jdbcTemplate.query("select * from customers WHERE name = ?", customerRowMapper, name);
  }

  @Override
  public Optional<Customer> findByEmail(String email) {
    try {
      return Optional.ofNullable(
          jdbcTemplate.queryForObject(
              "select * from customers WHERE email = ?", customerRowMapper, email));
    } catch (EmptyResultDataAccessException e) {
      logger.error("Got empty result", e);
      return Optional.empty();
    }
  }

  @Override
  public void deleteAll() {
    jdbcTemplate.update("DELETE FROM customers");
  }

  @Override
  public List<Customer> findByType(String type) {
    return jdbcTemplate.query("select * from customers WHERE type = ?", customerRowMapper, type);
  }

  @Override
  public void deleteByEmail(String email) {
    jdbcTemplate.update("delete from customers where email = ?", email);
  }

  @Override
  public boolean existEmail(String email) {
    int count =
        jdbcTemplate.queryForObject(
            "select count(*) from customers where email = ?", Integer.class, email);
    return count > 0 ? true : false;
  }

  @Override
  public boolean existId(UUID customerId) {
    int count =
            jdbcTemplate.queryForObject(
                    "select count(*) from customers where customer_id = ?", Integer.class, customerId.toString());
    return count > 0 ? true : false;
  }
}
