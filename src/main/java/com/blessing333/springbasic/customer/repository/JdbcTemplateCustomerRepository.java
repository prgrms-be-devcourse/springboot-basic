package com.blessing333.springbasic.customer.repository;

import com.blessing333.springbasic.customer.domain.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class JdbcTemplateCustomerRepository implements CustomerRepository {
    private static final String INSERT_SQL = "INSERT INTO customers(customer_id, name, email, created_at) VALUES (UUID_TO_BIN(:customerId), :name, :email, :cratedAt)";
    private static final String UPDATE_SQL = "UPDATE customers SET name = :name, email = :email, last_login_at = :lastLoginAt WHERE customer_id = UUID_TO_BIN(:customerId)";
    private static final String COUNT_SQL = "SELECT count(*) FROM customers";
    private static final String FIND_ALL_SQL = "SELECT * FROM customers";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)";
    private static final String DELETE_ALL_SQL = "DELETE FROM customers";
    private static final CustomerRowMapper customerRowMapper = new CustomerRowMapper();
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Customer insert(Customer customer) {
        var affectedRowCount = jdbcTemplate.update(INSERT_SQL, toParamMap(customer));
        if (affectedRowCount < 1) {
            throw new RuntimeException("Noting was inserted");
        }
        return customer;
    }

    @Override
    public void update(Customer customer) {
        var affectedRowCount = jdbcTemplate.update(UPDATE_SQL, toParamMap(customer));
        if (affectedRowCount < 1) {
            throw new RuntimeException("Noting was updated");
        }
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(COUNT_SQL, Collections.emptyMap(), Integer.class);
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        Customer customer;
        Map<String, byte[]> param = Collections.singletonMap("customerId", customerId.toString().getBytes());
        try {
            customer = jdbcTemplate.queryForObject(FIND_BY_ID_SQL, param, customerRowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException(e);
        }
        return Optional.ofNullable(customer);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL, Collections.emptyMap());
    }

    private Map<String, Object> toParamMap(Customer customer) {
        Map<String, Object> map = new HashMap<>();
        map.put("customerId", customer.getCustomerId().toString().getBytes());
        map.put("name", customer.getName());
        map.put("email", customer.getEmail());
        map.put("cratedAt", Timestamp.valueOf(customer.getCreatedAt()));
        map.put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        return map;
    }

    private static class CustomerRowMapper implements RowMapper<Customer> {
        private static UUID toUUID(byte[] bytes) {
            var byteBuffer = ByteBuffer.wrap(bytes);
            return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
        }

        @Override
        public Customer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            var customerName = resultSet.getString("name");
            var email = resultSet.getString("email");
            var customerId = toUUID(resultSet.getBytes("customer_id"));
            var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                    resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
            var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
            return Customer.customerBuilder()
                    .customerId(customerId)
                    .name(customerName)
                    .email(email)
                    .createdAt(createdAt)
                    .lastLoginAt(lastLoginAt)
                    .build();
        }
    }
}
