package com.programmers.springweekly.repository.customer;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.customer.CustomerType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@Profile({"test", "local"})
public class JdbcTemplateCustomerRepository implements CustomerRepository {

    private NamedParameterJdbcTemplate template;

    public JdbcTemplateCustomerRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Customer save(Customer customer) {
        String sql = "insert into customers values (:customerId, :customerName, :customerEmail, :customerType)";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerId", customer.getCustomerId().toString())
                .addValue("customerName", customer.getCustomerName())
                .addValue("customerEmail", customer.getCustomerEmail())
                .addValue("customerType", customer.getCustomerType().toString());

        try {
            template.update(sql, param);
            return customer;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public void update(Customer customer) {
        String sql = "update customers set customer_name = :customerName, customer_email = :customerEmail, customer_type = :customerType where customer_id = :customerId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerName", customer.getCustomerName())
                .addValue("customerEmail", customer.getCustomerEmail())
                .addValue("customerType", customer.getCustomerType().toString())
                .addValue("customerId", customer.getCustomerId().toString());

        template.update(sql, param);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        String sql = "select * from customers where customer_id = :customerId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerId", customerId.toString());

        try {
            Customer customer = template.queryForObject(sql, param, customerRowMapper());

            return Optional.of(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        String sql = "select * from customers";

        return template.query(sql, customerRowMapper());
    }

    @Override
    public List<Customer> getBlackList() {
        String sql = "select * from customers where customer_type = :customerType";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerType", CustomerType.BLACKLIST.toString());

        return template.query(sql, param, customerRowMapper());
    }

    @Override
    public void deleteById(UUID customerId) {
        String sql = "delete from customers where customer_id = :customerId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerId", customerId.toString());

        template.update(sql, param);
    }

    @Override
    public void deleteAll() {
        String sql = "delete from customers";

        SqlParameterSource param = new MapSqlParameterSource();

        template.update(sql, param);
    }

    @Override
    public boolean existById(UUID customerId) {
        String sql = "select * from customers where customer_id = :customerId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerId", customerId.toString());

        try {
            template.queryForObject(sql, param, customerRowMapper());
            
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    private RowMapper<Customer> customerRowMapper() {
        return ((resultSet, rowMap) ->
                Customer.builder()
                        .customerId(UUID.fromString(resultSet.getString("customer_id")))
                        .customerName(resultSet.getString("customer_name"))
                        .customerEmail(resultSet.getString("customer_email"))
                        .customerType(CustomerType.valueOf(resultSet.getString("customer_type")))
                        .build()
        );
    }

}
