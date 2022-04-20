package org.programmers.kdtspring.repository.user;

import org.programmers.kdtspring.entity.user.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

    private final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        Long customerId = resultSet.getLong("customer_id");
//        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at").toLocalDateTime() != null ?
//                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
    };

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private HashMap<String, Object> toParamMap(Customer customer) {
        return new HashMap<String, Object>() {{
            put("customerId", customer.getCustomerId());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        }};
    }

    @Override
    public Customer save(Customer customer) {
        int update = jdbcTemplate.update("INSERT INTO customers(customer_id, name, email, created_at) VALUES (:customerId, :name, :email, :createdAt)",
                toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Nothing was saved");
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        int update = jdbcTemplate.update("UPDATE customers SET name = :name, email = : email, last_login_at = :lastLoginAt WHERE customer_id = :customerId",
                toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(Long customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers where customer_id = :customerId",
                    Collections.singletonMap("customerId", customerId.toString()),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers where name = :name",
                    Collections.singletonMap("name", name),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }


    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers where email = :email",
                    Collections.singletonMap("email", email),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM customers", Collections.emptyMap());
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from customers", Collections.emptyMap(), Integer.class);
    }
}
