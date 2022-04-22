package org.prgrms.kdtspringdemo.domain.customer.repository;

import org.prgrms.kdtspringdemo.domain.customer.data.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class CustomerJdbcRepository implements CustomerRepository{
    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        String customerName = resultSet.getString("name");
        String customerEmail = resultSet.getString("email");
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        return new Customer(customerId, customerName, customerEmail, lastLoginAt, createdAt);
    };

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        }};
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public Customer insert(Customer customer) {
        String insertQuery = "INSERT INTO customers(customer_id, name, email, created_at) VALUES (UUID_TO_BIN(:customerId),:name,:email,:createdAt)";
        int update = jdbcTemplate.update(insertQuery,toParamMap(customer));

        if (update != 1) {
            logger.error("Customer 등록 에서 Nothing was inserted");
            throw new RuntimeException("Nothing was inserted");
        }

        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        String updateQuery = "UPDATE customers SET name = :name, email = :email, last_login_at = last_login_at WHERE customer_id = UUID_TO_BIN(:customerId)";
        int update = jdbcTemplate.update(updateQuery, toParamMap(customer));

        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }

        return customer;
    }

    @Override
    public int count() {
        String countQuery = "select count(*) from customers";
        return jdbcTemplate.queryForObject(countQuery, Collections.emptyMap(), Integer.class);
    }

    @Override
    public List<Customer> findAll() {
        String findAllQuery = "select * from customers";
        return jdbcTemplate.query(findAllQuery, customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        String findByIdQuery = "select * from customers WHERE customer_id = UUID_TO_BIN(:customerId)";

        try {
            return Optional.of(jdbcTemplate.queryForObject(findByIdQuery,
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        String findByNameQuery = "select * from customers WHERE name = :name";

        try {
            return Optional.of(jdbcTemplate.queryForObject(findByNameQuery,
                    Collections.singletonMap("customerId", name),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            String findByEmailQuery = "select * from customers WHERE email = :email";

            return Optional.of(jdbcTemplate.queryForObject(findByEmailQuery,
                    Collections.singletonMap("email", email),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        String deleteAllQuery = "DELETE FROM customers";
        jdbcTemplate.update(deleteAllQuery, Collections.emptyMap());
    }
}
