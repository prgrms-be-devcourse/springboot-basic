package com.prgrms.commandLineApplication.repository.customer;

import com.prgrms.commandLineApplication.customer.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

  private static final String NO_EXIST_CUSTOMER = "It doesn't exist";
  private final NamedParameterJdbcTemplate jdbcTemplate;

  public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Customer save(Customer customer) {
    SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(customer);
    jdbcTemplate.update("INSERT INTO customers(customer_id, customer_name, email) VALUES(:customerId, :customerName, :email)"
            , sqlParameterSource);
    return customer;
  }

  @Override
  public UUID update(UUID customerId, String customerName) {
    jdbcTemplate.update("UPDATE customers SET customer_name = :customerName WHERE customer_id = :customerId ",
            Map.of("customerId", customerId, "customerName", customerName));
    return customerId;
  }

  @Override
  public void deleteAll() {
    jdbcTemplate.update("DELETE FROM customers", Map.of());
  }

  @Override
  public void deleteById(UUID customerId) {
    jdbcTemplate.update("DELETE FROM customers WHERE customer_id = :customerId",
            Map.of("customerId", customerId));
  }

  @Override
  public List<Customer> findAll() {
    List<Customer> customerList = jdbcTemplate.query("SELECT * FROM customers", customerRowMapper);
    return customerList;
  }

  @Override
  public Customer findById(UUID customerId) {
    try {
      Customer customer = jdbcTemplate.queryForObject("SELECT * FROM customers WHERE customer_id = :customerId",
              Map.of("customerId", customerId), customerRowMapper);
      return customer;
    } catch (EmptyResultDataAccessException e) {
      throw new NoSuchElementException(NO_EXIST_CUSTOMER + " -> " + customerId);
    }
  }

  @Override
  public Customer findByEmail(String email) {
    try {
      Customer customer = jdbcTemplate.queryForObject("SELECT * FROM customers WHERE email = :email",
              Map.of("email", email), customerRowMapper);
      return customer;
    } catch (EmptyResultDataAccessException e) {
      throw new NoSuchElementException(NO_EXIST_CUSTOMER + " -> " + email);
    }
  }

  private RowMapper<Customer> customerRowMapper = (resultSet, rowNum) ->
          Customer.of(
                  UUID.fromString(resultSet.getString("customer_id")),
                  resultSet.getString("customer_name"),
                  resultSet.getString("email")
          );

}
