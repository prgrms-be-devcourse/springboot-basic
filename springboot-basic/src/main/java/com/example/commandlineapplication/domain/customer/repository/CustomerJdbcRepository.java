package com.example.commandlineapplication.domain.customer.repository;

import com.example.commandlineapplication.domain.customer.dto.mapper.CustomerMapper;
import com.example.commandlineapplication.domain.customer.dto.request.CustomerCreateRequest;
import com.example.commandlineapplication.domain.customer.dto.request.CustomerUpdateRequest;
import com.example.commandlineapplication.domain.customer.model.Customer;
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

  private final Logger LOG = LoggerFactory.getLogger(CustomerJdbcRepository.class);
  private final NamedParameterJdbcTemplate template;
  private final CustomerMapper customerMapper;

  public CustomerJdbcRepository(DataSource dataSource, CustomerMapper customerMapper) {
    this.template = new NamedParameterJdbcTemplate(dataSource);
    this.customerMapper = customerMapper;
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
  public void save(CustomerCreateRequest customerCreateRequest) {

    String sql = "insert into customer values (:customerId, :customerName, :customerEmail)";

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("customerId", customerCreateRequest.getCustomerId().toString())
        .addValue("customerName", customerCreateRequest.getName())
        .addValue("customerEmail", customerCreateRequest.getEmail());

    int saved = template.update(sql, param);

    if (saved != 1) {
      LOG.error("customer가 저장되지 않았습니다.");
      throw new RuntimeException("customer가 저장되지 않았습니다.");
    }
  }

  @Override
  public void update(UUID customerId, CustomerUpdateRequest customerUpdateRequest) {
    String sql = "update customer set customer_name = :customerName, customer_email = :customerEmail where customer_id = :customerId";

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("customerId", customerId.toString())
        .addValue("customerName", customerUpdateRequest.getName())
        .addValue("customerEmail", customerUpdateRequest.getEmail());

    int updated = template.update(sql, param);

    if (updated != 1) {
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

    if (deleted != 1) {
      LOG.error("customer가 삭제되지 않았습니다.");
      throw new RuntimeException("customer가 삭제되지 않았습니다.");
    }
  }

  @Override
  public void deleteAll() {
    String sql = "delete from customer";
    template.query(sql, rowMapper());
  }
}
