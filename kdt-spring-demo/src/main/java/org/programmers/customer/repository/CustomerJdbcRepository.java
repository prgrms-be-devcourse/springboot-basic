package org.programmers.customer.repository;

import org.programmers.customer.model.Customer;
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
public class CustomerJdbcRepository implements CustomerRepository {

    private final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CustomerJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var customerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ? resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
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

    @Override
    public void save(Customer customer) {
        var update = namedParameterJdbcTemplate.update("INSERT INTO customers(customer_id,name,email,created_at) VALUES(UUID_TO_BIN(:customerId),:name,:email,:createdAt)",
                toParamMap(customer));
        if (update != 1) throw new RuntimeException("Nothing was saved");
    }

    @Override
    public void update(String email, String name) {
        var paramMap = new HashMap<String, Object>() {{
            put("email", email);
            put("name", name);
        }};
        var update = namedParameterJdbcTemplate.update("UPDATE customers SET name = :name WHERE email = :email",
                paramMap);

        if (update != 1) throw new RuntimeException("Nothing was updated");
    }

    @Override
    public List<Customer> findAll() {
        return namedParameterJdbcTemplate.query("select * from customers", customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject("select * from customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                    Collections.singletonMap("customerId", customerId.toString().getBytes()), customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }

    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject("select * from customers WHERE email = :email", Collections.singletonMap("email", email), customerRowMapper));
        }catch (EmptyResultDataAccessException e){
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteByEmail(String email) {
        var paramMap = new HashMap<String, Object>() {{
            put("email", email);
        }};
        var delete = namedParameterJdbcTemplate.update("DELETE from customers WHERE email = :email", paramMap);

        if (delete != 1) throw new RuntimeException("Nothing was deleted");
    }

    @Override
    public void deleteAll() {
        namedParameterJdbcTemplate.update("DELETE from customers", Collections.emptyMap());
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
