package org.prgrms.memory;

import static org.prgrms.memory.query.CustomerSQL.*;

import java.util.NoSuchElementException;
import org.prgrms.customer.Customer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.dao.DataAccessException;
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
      throw new DataAccessException(
          "please check id *current id: " + customer.getId(), e) {
      };
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

    int deleteCount = jdbcTemplate.update(DELETE_BY_ID.getSql(), String.valueOf(id));
    if(deleteCount == 0) {
      return Optional.empty();
    }
    return beforeDeletion;
  }

  public Customer update(Customer customer) {
    int updateNum = jdbcTemplate.update(UPDATE.getSql(), customer.getName(),
        String.valueOf(customer.getId()));
    if (updateNum == 0) {
      throw new NoSuchElementException(
          "That ID could not be found *current ID : " + customer.getId());
    }
    return customer;
  }
}
