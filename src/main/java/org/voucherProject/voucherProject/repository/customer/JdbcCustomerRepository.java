package org.voucherProject.voucherProject.repository.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.voucherProject.voucherProject.entity.customer.Customer;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Slf4j
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private final String SELECT_BY_ID_SQL = "select * from customer where customer_id = :customerId";
    private final String SELECT_BY_NAME_SQL = "select * from customers where name  = :name";
    private final String SELECT_BY_EMAIL_SQL = "select * from customers where email  = :email";
    private final String SELECT_ALL_SQL = "select * from customers";
    private final String DELETE_ALL_SQL = "delete from customers";

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID_SQL,
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
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SELECT_BY_NAME_SQL,
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
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SELECT_BY_EMAIL_SQL,
                    Collections.singletonMap("email", customerEmail),
                    customerRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        return namedParameterJdbcTemplate.query(SELECT_ALL_SQL, customerRowMapper());
    }

    @Override
    public Customer save(Customer customer) {
        int update = namedParameterJdbcTemplate.update("insert into customers(customer_id, name, email, password, created_at) values (UUID_TO_BIN(:customerId), :name, :email, :password, :createdAt)",
                toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Nothing inserted");
        }
        return customer;
    }

    @Override
    public void deleteAll() {
        namedParameterJdbcTemplate.update(DELETE_ALL_SQL, Collections.emptyMap());
    }

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getCustomerName());
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
