package com.prgrms.springbootbasic.repository.customer;

import com.prgrms.springbootbasic.domain.customer.Customer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@Primary
public class CustomerJdbcRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate template;

    public CustomerJdbcRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Customer save(Customer customer) {
        String sql = "insert into customers values(:customerId, :customerName, :customerEmail, :customerCreateAt)";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerId", customer.getCustomerId().toString())
                .addValue("customerName", customer.getName())
                .addValue("customerEmail", customer.getEmail())
                .addValue("customerCreateAt", customer.getCreateAt());

        template.update(sql, param);
        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        String sql = "select * from customers where customer_id = :customerId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerId", customerId.toString());

        Customer customer = template.queryForObject(sql, param, customerRowMapper());
        return Optional.ofNullable(customer);
    }

    @Override
    public List<Customer> findAll() {
        String sql = "select * from customers";

        List<Customer> customers = template.query(sql, customerRowMapper());
        return customers;
    }

    @Override
    public List<Customer> findByCreatedAt() {
        String sql = "select * from customers ORDER BY customer_createAt ASC";

        List<Customer> customers = template.query(sql, customerRowMapper());
        return customers;
    }

    @Override
    public void update(Customer customer) {
        String sql = "update customers set customer_Name = :customerName, customer_Email = :customerEmail where customer_id = :customerId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerId", customer.getCustomerId().toString())
                .addValue("customerName", customer.getName())
                .addValue("customerEmail", customer.getEmail());
        template.update(sql, param);
    }


    @Override
    public boolean checkCustomerId(UUID customerId) {
        String sql = "select * from customers where customer_id = :customerId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerId", customerId.toString());
        try {
            template.queryForObject(sql, param, customerRowMapper());
            return true;
        } catch (EmptyResultDataAccessException e) {
            log.warn("고객의 ID가 존재하는지 체크했으나 없어서 예외 발생함", e.getMessage());
            return false;
        }
    }

    @Override
    public int deleteById(UUID customerId) {
        String sql = "delete from customers where customer_id = :customerId";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerId", customerId.toString());
        return template.update(sql, param);
    }


    @Override
    public void deleteAll() {
        String sql = "delete from customers";

        SqlParameterSource param = new MapSqlParameterSource();

        template.update(sql, param);
    }


    private RowMapper<Customer> customerRowMapper() {
        return (resultSet, rowMap) -> {
            UUID customerId = UUID.fromString(resultSet.getString("customer_id"));
            String name = resultSet.getString("customer_name");
            String email = resultSet.getString("customer_email");
            LocalDateTime createAt = resultSet.getTimestamp("customer_createAt").toLocalDateTime();

            return new Customer(customerId, name, email, createAt);
        };
    }
}
