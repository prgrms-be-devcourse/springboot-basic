package com.example.commandlineapplication.domain.customer.repository;

import com.example.commandlineapplication.domain.customer.Customer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

  private static final Logger LOG = LoggerFactory.getLogger(CustomerJdbcRepository.class);
  private static final int SUCCESS_EXECUTE = 1;
  private final NamedParameterJdbcTemplate template;

  public CustomerJdbcRepository(DataSource dataSource) {
    this.template = new NamedParameterJdbcTemplate(dataSource);
  }

  private RowMapper<Customer> rowMapper() {
    return ((resultSet, rowMap) ->
        new Customer(
            UUID.fromString(resultSet.getString("customer_id")),
            resultSet.getString("customer_name"),
            resultSet.getString("customer_email")
        )
    );
  }

  @Override
  public void save(Customer customer) {

    String sql = "insert into customer values (:customerId, :customerName, :customerEmail)";

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("customerId", customer.getCustomerId().toString())
        .addValue("customerName", customer.getName())
        .addValue("customerEmail", customer.getEmail());

    int saved = template.update(sql, param);

    if (saved != SUCCESS_EXECUTE) {
      LOG.error("customer가 저장되지 않았습니다.");
      throw new RuntimeException("customer가 저장되지 않았습니다.");
    }
  }

  @Override
  public void update(Customer customer) {
    String sql = "update customer set customer_name = :customerName, customer_email = :customerEmail where customer_id = :customerId";

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("customerId", customer.getCustomerId().toString())
        .addValue("customerName", customer.getName())
        .addValue("customerEmail", customer.getEmail());

    int updated = template.update(sql, param);

    if (updated != SUCCESS_EXECUTE) {
      LOG.error("customer가 수정되지 않았습니다.");
      throw new RuntimeException("customer가 수정되지 않았습니다.");
    }
  }

  @Override
  public Optional<Customer> findById(UUID customerId) {
    String sql = "select * from customer where customer_id = :customerId";

    try {
      SqlParameterSource param = new MapSqlParameterSource()
          .addValue("customerId", customerId.toString());

      Customer customer = template.queryForObject(sql, param, rowMapper());

      return Optional.ofNullable(customer);
    } catch (EmptyResultDataAccessException e) {
      LOG.error("customerId가 존재하지 않습니다.", e);
      return Optional.empty();
    }
  }

  @Override
  public List<Customer> findAll() {
    String sql = "select * from customer";

    return template.query(sql, rowMapper());
  }

  @Override
  public void deleteById(UUID customerId) {
    String sql = "delete from customer where customer_id = :customerId";

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("customerId", customerId.toString());

    int deleted = template.update(sql, param);

    if (deleted != SUCCESS_EXECUTE) {
      LOG.error("customer가 삭제되지 않았습니다.");
      throw new RuntimeException("customer가 삭제되지 않았습니다.");
    }
  }
}
