package org.prgrms.kdt.domain.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

@Slf4j
@Repository
@Primary
public class CustomerNamedJdbcRepository implements CustomerRepository {

    private static final String SELECT_BY_NAME_SQL = "SELECT * FROM customers WHERE name = :name";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM customers WHERE customer_id = :customerId";
    private static final String SELECT_BY_EMAIL_SQL = "SELECT * FROM customers WHERE email = :email";
    private static final String SELECT_ALL_SQL = "SELECT * FROM customers";
    private static final String COUNT_SQL = "SELECT COUNT(*) FROM customers";
    private static final String INSERT_SQL = "INSERT INTO customers(customer_id, name, email, created_at) VALUES (:customerId, :name, :email, :createdAt)";
    private static final String UPDATE_BY_ID_SQL = "UPDATE customers SET name = :name, email = :email, last_login_at = :lastLoginAt WHERE customer_id = :customerId";
    private static final String DELETE_ALL_SQL = "DELETE FROM customers";

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerId = resultSet.getLong("customer_id");
        var customerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Customer(customerId, Name.valueOf(customerName), Email.valueOf(email), createdAt, lastLoginAt);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerNamedJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId());
            put("name", customer.getName().name());
            put("email", customer.getEmail().email());
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        }};
    }

    @Override
    public Customer insert(Customer customer) {
        var update = jdbcTemplate.update(INSERT_SQL, toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        var update = jdbcTemplate.update(UPDATE_BY_ID_SQL, toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(Long customerId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(SELECT_BY_ID_SQL,
                            Collections.singletonMap("customerId", customerId),
                            customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(Name name) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(SELECT_BY_NAME_SQL,
                            Collections.singletonMap("name", name),
                            customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(COUNT_SQL, Collections.emptyMap(), Integer.class);
    }

    @Override
    public Optional<Customer> findByEmail(Email email) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(SELECT_BY_EMAIL_SQL,
                            Collections.singletonMap("email", email),
                            customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL, Collections.emptyMap());
    }
}
