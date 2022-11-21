package com.program.commandLine.repository;

import com.program.commandLine.customer.Customer;
import com.program.commandLine.customer.CustomerFactory;
import com.program.commandLine.customer.CustomerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static com.program.commandLine.util.JdbcUtil.toUUID;

@Component(value = "customerRepository")
public class CustomerJdbcRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final CustomerFactory customerFactory;

    public CustomerJdbcRepository(DataSource dataSource, CustomerFactory customerFactory) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.customerFactory = customerFactory;
    }

    private final RowMapper<Customer> customerRowMapper = new RowMapper<Customer>() {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            CustomerType customerType = CustomerType.getType(rs.getString("type"));
            String customerName = rs.getString("name");
            UUID customerId = toUUID(rs.getBytes("customer_id"));
            String email = rs.getString("email");
            LocalDateTime lastLoginAt = rs.getTimestamp("last_login_at") != null ?
                    rs.getTimestamp("last_login_at").toLocalDateTime() : null;

            return customerFactory.createCustomer(customerType, customerId, customerName, email, lastLoginAt);
        }
    };


    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("type", customer.getCustomerType().getString());
            put("email", customer.getEmail());
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        }};
    }

    @Override
    public Customer insert(Customer customer) {
        String sql = "INSERT INTO customers(customer_id,type,name,email, last_login_at)" +
                "  VALUES (UUID_TO_BIN(:customerId),:type,:name,:email,:lastLoginAt)";
        int update = jdbcTemplate.update(sql, toParamMap(customer));
        if (update != 1) throw new RuntimeException("Nothing was inserted!");
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        String sql = "UPDATE customers SET name= :name, email= :email, last_login_at= :lastLoginAt  WHERE customer_id = UUID_TO_BIN(:customerId)";
        int update = jdbcTemplate.update(sql, toParamMap(customer));
        if (update != 1) throw new RuntimeException("Nothing was updated!");
        return customer;
    }

    @Override
    public int count() {
        String sql = "select count(*) from customers";
        return jdbcTemplate.getJdbcTemplate().queryForObject(sql, Integer.class);
    }

    @Override
    public List<Customer> findAll() {
        String sql = "select * from customers";
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        String sql = "select * from customers WHERE customer_id = UUID_TO_BIN(:customerId)";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                    Collections.singletonMap("customerId", customerId.toString().getBytes()), customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        String sql = "select * from customers WHERE name = :name";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, Collections.singletonMap("name", name), customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        String sql = "select * from customers WHERE email = :email";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, Collections.singletonMap("email", email), customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM customers";
        jdbcTemplate.getJdbcTemplate().update(sql);
    }
}
