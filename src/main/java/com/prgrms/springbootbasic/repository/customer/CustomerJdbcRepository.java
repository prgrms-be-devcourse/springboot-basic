package com.prgrms.springbootbasic.repository.customer;

import com.prgrms.springbootbasic.domain.customer.Customer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
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
                .addValue("customerId", customer.getCustomerId())
                .addValue("customerName", customer.getName())
                .addValue("customerEmail", customer.getEmail())
                .addValue("customerCreateAt", customer.getCreateAt());

        template.update(sql, param);
        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        String sql = "select * from customer where customer_id = :customerId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerId", customerId);

        Customer customer = template.queryForObject(sql, param, customerRowMapper());
        return Optional.ofNullable(customer);
    }

    @Override
    public Optional<Customer> findByCreateAt(LocalDateTime createAt) {
        String sql = "select * from customers where create_at = :createAt";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("createAt", createAt);

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
    public Optional<Customer> update(Customer customer) {
        String sql = "update customers set customerName = :customerName, customerEmail = :customerEmail where customer_id = :customerId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("cutomerName", customer.getName())
                .addValue("customerEmail", customer.getEmail());

        int rows = template.update(sql, param);
        if (rows == 1) {
            return Optional.of(customer);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> deleteById(UUID customerId) {
        String sql = "delete from customers where customer_id = :customerId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerId", customerId);

        int deletedRows = template.update(sql, param);
        if (deletedRows == 1) {
            return Optional.of(null);
        } else {
            return Optional.empty();
        }
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
