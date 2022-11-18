package org.prgrms.memory;

import static org.prgrms.query.CustomerSQL.*;

import customer.Customer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDBMemory {

  private final JdbcTemplate jdbcTemplate;

  public CustomerDBMemory(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
    UUID id = UUID.fromString(resultSet.getString("id"));
    String name = resultSet.getString("name");

    return new Customer(id, name);
  };

  public Customer save(Customer customer) {
    try {
      jdbcTemplate.update(INSERT.getSql(), String.valueOf(customer.getId()),
          customer.getName());
    } catch (DataAccessException e) {
      throw new DuplicateKeyException(
          "This member's ID already exists *current id: " + customer.getId());
    }
    return customer;
  }

  public Optional<Customer> findById(UUID id) {
    Customer customer;
    try {
      customer = jdbcTemplate.queryForObject(FIND_BY_ID.getSql(), customerRowMapper,
          String.valueOf(id));
    } catch (DataAccessException e) {
      return Optional.empty();
    }
    return Optional.ofNullable(customer);
  }

  public List<Customer> findAll() {
    return jdbcTemplate.query(FIND_ALL.getSql(), customerRowMapper);
  }

  public void deleteAll() {
    jdbcTemplate.update(DELETE_ALL.getSql());
  }

  public Optional<Customer> deleteById(UUID id) {
    Optional<Customer> beforeDeletion = findById(id);
    if (beforeDeletion.isPresent()) {
      jdbcTemplate.update(DELETE_BY_ID.getSql(), String.valueOf(id));
    }
    return beforeDeletion;
  }
}
