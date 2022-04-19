package com.blessing333.springbasic.customer.repository;

import com.blessing333.springbasic.customer.domain.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;
//TODO 래퍼클래스로 감싸 예외 통합하기
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
    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return Customer.createNewCustomerWithAllArgument(customerId, customerName, email, createdAt, lastLoginAt);
    };
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
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

    @Override
    public UUID insert(Customer customer) {
        var affectedRowCount = jdbcTemplate.update(INSERT_SQL, toParamMap(customer));
        if (affectedRowCount < 1) {
            throw new RuntimeException("Noting was inserted");
        }
        return customer.getCustomerId();
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
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_SQL,
                Collections.singletonMap("customerId", customerId.toString().getBytes()),
                customerRowMapper));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL, Collections.emptyMap());
    }
}
