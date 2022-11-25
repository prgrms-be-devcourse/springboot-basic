package org.prgrms.voucherapplication.customer.repository;

import org.prgrms.voucherapplication.customer.entity.Customer;
import org.prgrms.voucherapplication.customer.NothingInsertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Primary
public class CustomerNamedJdbcRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerNamedJdbcRepository.class);
    private static final String NOTHING_INSERT = "Nothing was inserted";
    private static final String EMPTY_RESULT = "Got empty result";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        LocalDateTime createAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Customer(customerId, customerName, email, lastLoginAt, createAt);
    };

    public CustomerNamedJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer insert(Customer customer) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerId", customer.getCustomerId().toString().getBytes());
        paramMap.put("name", customer.getName());
        paramMap.put("email", customer.getEmail());
        paramMap.put("createdAt", Timestamp.valueOf(customer.getCreateAt()));

        int update = jdbcTemplate.update("INSERT INTO customers(customer_id, name, email, created_at) VALUES (UUID_TO_BIN(:customerId), :name, :email, :createdAt)",
                paramMap);
        if (update != 1) {
            throw new NothingInsertException(NOTHING_INSERT);
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerId", customer.getCustomerId().toString().getBytes());
        paramMap.put("name", customer.getName());
        paramMap.put("email", customer.getEmail());
        paramMap.put("lastLoginAt", Timestamp.valueOf(customer.getLastLoginAt()));

        int update = jdbcTemplate.update("UPDATE customers SET name = :name, email = :email, last_login_at = :lastLoginAt WHERE customer_id = UUID_TO_BIN(:customerId)",
            paramMap
        );
        if (update != 1) {
            throw new NothingInsertException(NOTHING_INSERT);
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
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error(EMPTY_RESULT, e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers WHERE name = :name",
                    Collections.singletonMap("name", name),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error(EMPTY_RESULT, e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers WHERE email = :email",
                    Collections.singletonMap("email", email),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error(EMPTY_RESULT, e);
            return Optional.empty();
        }
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
