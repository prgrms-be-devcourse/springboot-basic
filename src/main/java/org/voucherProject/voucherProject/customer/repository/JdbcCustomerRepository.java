package org.voucherProject.voucherProject.customer.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.voucherProject.voucherProject.customer.entity.Customer;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Slf4j
@Primary
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = namedParameterJdbcTemplate;
    }

    private final String SELECT_BY_ID_SQL = "select * from customers where customer_id = UUID_TO_BIN(:customerId)";
    private final String SELECT_BY_NAME_SQL = "select * from customers where name  = :name";
    private final String SELECT_BY_EMAIL_SQL = "select * from customers where email  = :email";
    private final String SELECT_ALL_SQL = "select * from customers";
    private final String DELETE_ALL_SQL = "delete from customers";
    private final String INSERT_SQL = "insert into customers(customer_id, name, email, password, created_at) values (UUID_TO_BIN(:customerId), :name, :email, :password, :createdAt)";

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID_SQL,
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    customerRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            log.error("Empty Result -> {}", e.toString());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String customerName) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_NAME_SQL,
                    Collections.singletonMap("name", customerName),
                    customerRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String customerEmail) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_EMAIL_SQL,
                    Collections.singletonMap("email", customerEmail),
                    customerRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, customerRowMapper());
    }

    @Override
    public Customer save(Customer customer) {
        int update = jdbcTemplate.update(INSERT_SQL,
                toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Nothing inserted");
        }
        return customer;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL, Collections.emptyMap());
    }

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getCustomerName());
            put("password", customer.getPassword());
            put("email", customer.getCustomerEmail());
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        }};
    }

    private static final RowMapper<Customer> customerRowMapper() {
        return (resultSet, i) -> {
            String customerName = resultSet.getString("name");
            UUID customerId = toUUID(resultSet.getBytes("customer_id"));
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
            LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                    resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
            return new Customer(customerId, customerName, email, password, createdAt, lastLoginAt);
        };
    }

    static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        UUID customerId = new UUID(byteBuffer.getLong(), byteBuffer.getLong());
        return customerId;
    }
}
