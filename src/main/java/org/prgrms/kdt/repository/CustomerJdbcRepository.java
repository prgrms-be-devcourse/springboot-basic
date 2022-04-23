package org.prgrms.kdt.repository;

import org.prgrms.kdt.model.customer.Customer;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Customer> customerRowMapper = (resultSet, rowNum) -> {
        var name = resultSet.getString("name");
        var email = resultSet.getString("email");
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        return new Customer(customerId, name, email, lastLoginAt, createdAt);
    };

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer insert(Customer customer) throws DuplicateKeyException {
        var paramMap = new HashMap<String, Object>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("createdAt", customer.getCreateAt());
            put("lastLoginAt", customer.getLastLoginAt());
        }};
        jdbcTemplate.update("INSERT INTO customers(customer_id, name, email, created_at, last_login_at) VALUES (UUID_TO_BIN(:customerId), :name, :email, :createdAt, :lastLoginAt)", paramMap);
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customers", customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                Collections.singletonMap("customerId", customerId.toString().getBytes()),
                customerRowMapper));
    }

    @Override
    public Customer findByEmail(String email) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject("SELECT * FROM customers WHERE email = :email",
                Collections.singletonMap("email", email),
                customerRowMapper);

    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM customers", Collections.emptyMap());
    }


    static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
