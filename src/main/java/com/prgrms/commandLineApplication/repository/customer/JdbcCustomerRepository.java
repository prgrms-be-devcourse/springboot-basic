package com.prgrms.commandLineApplication.repository.customer;

import com.prgrms.commandLineApplication.customer.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public class JdbcCustomerRepository implements CustomerRepository {

  private static final String NO_EXIST_CUSTOMER = "It doesn't exist";
  private static final String SAVE_CUSTOMER_QUERY = "INSERT INTO customers(customer_id, customer_name, email) VALUES(:customerId, :customerName, :email)";
  private static final String UPDATE_NAME_QUERY = "UPDATE customers SET customer_name = :customerName WHERE email = :email ";
  private static final String DELETE_ALL_QUERY = "DELETE FROM customers";
  private static final String DELETE_BY_ID_QUERY = "DELETE FROM customers WHERE customer_id = :customerId";
  private static final String FIND_ALL_QUERY = "SELECT * FROM customers";
  private static final String FIND_BY_ID_QUERY = "SELECT * FROM customers WHERE customer_id = :customerId";
  private static final String FIND_BY_EMAIL_QUERY = "SELECT * FROM customers WHERE email = :email";

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Customer save(Customer customer) {
    SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(customer);
    jdbcTemplate.update(SAVE_CUSTOMER_QUERY, sqlParameterSource);
    return customer;
  }

  @Override
  public String update(String email, String customerName) {
    jdbcTemplate.update(UPDATE_NAME_QUERY, Map.of("email", email, "customerName", customerName));
    return email;
  }

  @Override
  public void deleteAll() {
    jdbcTemplate.update(DELETE_ALL_QUERY, Collections.emptyMap());
  }

  @Override
  public void deleteById(UUID customerId) {
    jdbcTemplate.update(DELETE_BY_ID_QUERY, Map.of("customerId", customerId));
  }

  @Override
  public List<Customer> findAll() {
    List<Customer> customerList = jdbcTemplate.query(FIND_ALL_QUERY, customerRowMapper);
    return customerList;
  }

  @Override
  public Customer findById(UUID customerId) {
    try {
      Customer customer = jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, Map.of("customerId", customerId), customerRowMapper);
      return customer;
    } catch (EmptyResultDataAccessException e) {
      throw new NoSuchElementException(NO_EXIST_CUSTOMER + " -> " + customerId, e);
    }
  }

  @Override
  public Customer findByEmail(String email) {
    try {
      Customer customer = jdbcTemplate.queryForObject(FIND_BY_EMAIL_QUERY, Map.of("email", email), customerRowMapper);
      return customer;
    } catch (EmptyResultDataAccessException e) {
      throw new NoSuchElementException(NO_EXIST_CUSTOMER + " -> " + email, e);
    }
  }

  private RowMapper<Customer> customerRowMapper = (resultSet, rowNum) ->
          Customer.of(
                  UUID.fromString(resultSet.getString("customer_id")),
                  resultSet.getString("customer_name"),
                  resultSet.getString("email")
          );

}
