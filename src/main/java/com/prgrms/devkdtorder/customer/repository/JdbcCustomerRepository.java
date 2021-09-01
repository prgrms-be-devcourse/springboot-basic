package com.prgrms.devkdtorder.customer.repository;

import com.prgrms.devkdtorder.customer.domain.BlackCustomers;
import com.prgrms.devkdtorder.customer.domain.Customer;
import com.prgrms.devkdtorder.customer.domain.CustomerType;
import com.prgrms.devkdtorder.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);


    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        var customerId = Utils.toUUID(resultSet.getBytes("customer_id"));
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime().truncatedTo(ChronoUnit.MILLIS);
        var customerType = CustomerType.valueOf(resultSet.getString("customer_type"));
        return new Customer(customerId, customerName, email, lastLoginAt, createdAt, customerType);
    };


    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
            put("customerType", customer.getCustomerType().name());
        }};
    }

    @Override
    public Customer insert(Customer customer) {
        String sql = "INSERT INTO customers(customer_id, name, email, created_at, customer_type) VALUES (UNHEX(REPLACE(:customerId, '-', '')), :name, :email, :createdAt, :customerType)";
        int insert = jdbcTemplate.update(sql, toParamMap(customer));
        if (insert != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }


    @Override
    public Customer update(Customer customer) {
        String sql = "UPDATE customers SET name = :name, email = :email, last_login_at = :lastLoginAt, customer_type = :customerType WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))";
        int update = jdbcTemplate.update(sql, toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return customer;
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from customers", Collections.emptyMap(), Integer.class);
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customers", customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            String sql = "SELECT * FROM customers WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))";
            Customer customer = jdbcTemplate.queryForObject(sql,
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    customerRowMapper);
            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }

    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            String sql = "SELECT * FROM customers WHERE name = :name";
            Customer customer = jdbcTemplate.queryForObject(sql,
                    Collections.singletonMap("name", name),
                    customerRowMapper);
            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }


    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            String sql = "SELECT * FROM customers WHERE email = :email";
            Customer customer = jdbcTemplate.queryForObject(sql,
                    Collections.singletonMap("email", email),
                    customerRowMapper);
            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.getJdbcTemplate().update("DELETE FROM customers");
    }

    @Override
    public BlackCustomers findAllBlackCustomers() {
        try {
            String sql = "SELECT * FROM customers WHERE customer_type = :customerType";
            List<Customer> blackCustomers = jdbcTemplate.query(
                    sql,
                    Collections.singletonMap("customerType", CustomerType.BLACK.name()),
                    customerRowMapper);
            return BlackCustomers.valueOf(blackCustomers);
        } catch (DataAccessException e) {
            logger.error("Query fails", e);
            return BlackCustomers.empty();
        }
    }


}
