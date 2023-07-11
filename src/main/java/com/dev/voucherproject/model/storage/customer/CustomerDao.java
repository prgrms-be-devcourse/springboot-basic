package com.dev.voucherproject.model.storage.customer;

import com.dev.voucherproject.model.customer.Customer;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public class CustomerDao {
    private static final String INSERT = "INSERT INTO customers(customer_id, name, email, created_at) " +
        "VALUES (UUID_TO_BIN(:customerId), :name, :email, :createdAt)";

    private static final String FIND_ALL = "SELECT * FROM customers";

    private static final String FIND_BY_UUID = "select * from customers where customer_id = UUID_TO_BIN(:customerId)";

    private static final String DELETE_ALL = "DELETE FROM customers";

    private static final String FIND_BY_NAME = "select * from customers where name = :name";

    private static final String FIND_BY_EMAIL = "select * from customers where email = :email";

    private static final String DELETE_BY_UUID = "DELETE FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Customer> rowMapper = (resultSet, i) -> {
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        return new Customer(customerId, customerName, email, createdAt);
    };

    private SqlParameterSource toParamMap(Customer customer) {
        return new MapSqlParameterSource()
            .addValue("customerId", customer.getCustomerId().toString().getBytes())
            .addValue("name", customer.getName())
            .addValue("email", customer.getEmail())
            .addValue("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
    }

    public void insert(Customer customer) {
        try {
            jdbcTemplate.update(INSERT, toParamMap(customer));
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException(MessageFormat.format("{} 는 이미 존재하는 이메일입니다.", customer.getEmail()));
        }
    }

    public List<Customer> findAll() {
        return jdbcTemplate.query(FIND_ALL, rowMapper);
    }

    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(FIND_BY_UUID, Collections.singletonMap("customerId", customerId.toString().getBytes()), rowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(FIND_BY_NAME, Collections.singletonMap("name", name), rowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(FIND_BY_EMAIL, Collections.singletonMap("email", email), rowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void update(Customer customer) {
        try {
            jdbcTemplate.update("UPDATE customers SET email = :email WHERE customer_id  = UUID_TO_BIN(:customerId)", toParamMap(customer));
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException(MessageFormat.format("{} 는 이미 사용중인 고객 이메일입니다.", customer.getEmail()));
        }
    }

    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL, Collections.emptyMap());
    }

    public void deleteById(UUID customerId) {
        try {
            jdbcTemplate.update(DELETE_BY_UUID, Collections.singletonMap("customerId", customerId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException(MessageFormat.format("{} 는 존재하지 않는 고객 아이디입니다.", customerId));
        }
    }

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        UUID uuid = new UUID(byteBuffer.getLong(), byteBuffer.getLong());
        return uuid;
    }
}
