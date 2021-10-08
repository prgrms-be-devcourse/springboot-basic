package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerName = resultSet.getString("customer_name");
        var customerEmail = resultSet.getString("customer_email");
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;

        return new Customer(customerId, customerName, customerEmail, createdAt, lastLoginAt);
    };
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    static UUID toUUID(final byte[] bytes) {
        final var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private Map<String, Object> toParamMap(final Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("customerName", customer.getName());
            put("customerEmail", customer.getEmail());
            put("cratedAt", Timestamp.valueOf(customer.getCreatedAt()));
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        }};
    }

    @Override
    public Customer insert(final Customer customer) {
        final var update = jdbcTemplate.update("INSERT INTO customers(customer_id, customer_name, customer_email, created_at) VALUES (UUID_TO_BIN(:customerId), :customerName, :customerEmail, :cratedAt)",
                toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
        return customer;
    }

    @Override
    public Customer update(final Customer customer) {
        final var update = jdbcTemplate.update("UPDATE customers SET customer_name = :customerName, customer_email = :customerEmail, last_login_at = :lastLoginAt WHERE customer_id = UUID_TO_BIN(:customerId)",
                toParamMap(customer)
        );
        if (update != 1) {
            throw new RuntimeException("Noting was updated");
        }
        return customer;
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from customers", Collections.emptyMap(), Integer.class);
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(final UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    customerRowMapper));
        } catch (final EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(final String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers WHERE customer_name = :customerName",
                    Collections.singletonMap("name", name),
                    customerRowMapper));
        } catch (final EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(final String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers WHERE customer_email = :customerEmail",
                    Collections.singletonMap("email", email),
                    customerRowMapper));
        } catch (final EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.getJdbcTemplate().update("DELETE FROM customers", Collections.emptyMap());
    }
}
