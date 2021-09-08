package org.prgrms.kdt.engine.customer.repository;

import org.prgrms.kdt.engine.customer.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class CustomerNamedJdbcRepository implements CustomerRepository{
    private static final Logger logger = LoggerFactory.getLogger(CustomerNamedJdbcRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var customerName = resultSet.getString("name");
        var customerEmail = resultSet.getString("email");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
            resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        return new Customer(customerId, customerName, customerEmail, createdAt, lastLoginAt);
    };

    public CustomerNamedJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(CustomerSql.COUNT.getSql(), Collections.emptyMap(), Integer.class);
    }

    @Override
    public Customer insert(Customer customer) {
        var insert = jdbcTemplate.update(CustomerSql.INSERT.getSql(), toParamMap(customer));
        if (insert != 1) throw new RuntimeException("Nothing was inserted");
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        var update = jdbcTemplate.update(CustomerSql.UPDATE.getSql(), toParamMap(customer));
        if (update != 1) throw new RuntimeException("Nothing was updated");
        return customer;
    }

    @Override
    public Optional<List<Customer>> findAll() {
        try {
            return Optional.of(jdbcTemplate.query(CustomerSql.SELECT_ALL.getSql(), customerRowMapper));
        } catch (DataAccessException e) {
            logger.error("Got Data Access Error", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(
                CustomerSql.SELECT_BY_ID.getSql(),
                Collections.singletonMap("customerId", customerId.toString().getBytes()),
                customerRowMapper));
        } catch (DataAccessException e) {
            logger.error("Got Data Access Error", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(
                CustomerSql.SELECT_BY_NAME.getSql(),
                Collections.singletonMap("name", name),
                customerRowMapper));
        } catch (DataAccessException e) {
            logger.error("Got Data Access Error", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(
                CustomerSql.SELECT_BY_EMAIL.getSql(),
                Collections.singletonMap("email", email),
                customerRowMapper));
        } catch (DataAccessException e) {
            logger.error("Got Data Access Error", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(CustomerSql.DELETE_ALL.getSql(), Collections.emptyMap());
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private Map<String, Object> toParamMap(Customer customer) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", customer.getCustomerId().toString().getBytes());
        paramMap.put("name", customer.getName());
        paramMap.put("email", customer.getEmail());
        paramMap.put("createdAt", customer.getCreatedAt());
        paramMap.put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        return paramMap;
    }
}
