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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import static com.program.commandLine.util.JdbcUtil.toUUID;

@Component(value = "customerRepository")
public class CustomerJdbcRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final CustomerFactory customerFactory;

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate, CustomerFactory customerFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerFactory = customerFactory;
    }

    private final RowMapper<Customer> customerRowMapper = new RowMapper<Customer>() {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            var customerType = rs.getString("type");
            var customerName = rs.getString("name");
            var customerId = toUUID(rs.getBytes("customer_id"));
            var email = rs.getString("email");
            var createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            var lastLoginAt = rs.getTimestamp("last_login_at") != null ?
                    rs.getTimestamp("last_login_at").toLocalDateTime() : null;

            return customerFactory.createCustomer(CustomerType.getType(customerType),customerId, customerName, email, lastLoginAt, createdAt);
        }
    };


    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("type", customer.getCustomerType().getString());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
            put("createdAt", customer.getCreatedAt());
        }};
    }

    @Override
    public Customer insert(Customer customer) {
        var update = jdbcTemplate.update("INSERT INTO customers(customer_id,type,name,email, created_at)" +
                "  VALUES (UUID_TO_BIN(:customerId),:type,:name,:email,:createdAt)", toParamMap(customer));
        if (update != 1) throw new RuntimeException("Nothing was inserted!");
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        var update = jdbcTemplate.update("UPDATE customers SET name= :name, email= :email, last_login_at= :lastLoginAt  WHERE customer_id = UUID_TO_BIN(:customerId)", toParamMap(customer));
        if (update != 1) throw new RuntimeException("Nothing was updated!");
        return customer;
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from customers", Collections.EMPTY_MAP, Integer.class);
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                    Collections.singletonMap("customerId", customerId.toString().getBytes()), customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers WHERE name = :name", Collections.singletonMap("name", name), customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers WHERE email = :email", Collections.singletonMap("email", email), customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.getJdbcTemplate().update("DELETE FROM customers");
    }
}
