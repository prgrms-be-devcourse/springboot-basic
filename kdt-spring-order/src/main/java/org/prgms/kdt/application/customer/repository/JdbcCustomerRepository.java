package org.prgms.kdt.application.customer.repository;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.prgms.kdt.application.customer.domain.Customer;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
@Slf4j
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Customer(customerId, customerName, email, createdAt);
    };

    static UUID toUUID(byte[] bytes) throws SQLException {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes(StandardCharsets.UTF_8));
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
        }};
    }

    @Override
    public List<Customer> getBlacklist() {
        return null;
    }

    @Override
    public Customer insert(Customer customer) {
        String sql = "INSERT INTO customers(customer_id, name, email, created_at) VALUES (UNHEX(REPLACE(:customerId, '-', '')), :name, :email, :createdAt)";
        int update = jdbcTemplate.update(sql, toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        String sql = "UPDATE customers SET name = :name, email = :email WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))";
        int update = jdbcTemplate.update(sql, toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        String sql = "select * from customers";
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        String sql = "select * from customers where customer_id = UNHEX(REPLACE(:customerId, '-', ''))";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                Collections.singletonMap("customerId", customerId.toString().getBytes()),
                customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        String sql = "select * from customers where name = :name";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                Collections.singletonMap("name", name),
                customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result", e);
            return Optional.empty();
        }
    }


    @Override
    public int delete(UUID customerId) {
        String sql = "DELETE FROM customers WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))";
        return jdbcTemplate.update(sql, Collections.singletonMap("customerId", customerId.toString().getBytes()));
    }

    @Override
    public int deleteAll() {
        String sql = "DELETE FROM customers";
        return jdbcTemplate.update(sql, Collections.emptyMap());
    }
}
