package org.prgrms.springbootbasic.repository;

import org.prgrms.springbootbasic.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

import static org.prgrms.springbootbasic.util.UUIDUtil.toUUID;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(CustomerRepositoryImpl.class);
    public static final String INSERT_SQL = "INSERT INTO customers(customer_id, name, email, last_login_at, created_at) VALUES(UUID_TO_BIN(:customerId), :name, :email, :lastLoginAt, :createdAt)";
    public static final String UPDATE_SQL = "UPDATE customers SET name= :name, last_login_at = :lastLoginAt WHERE customer_id = UUID_TO_BIN(:customerId)";
    public static final String SELECT_BY_ID_SQL = "SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)";
    public static final String SELECT_ALL = "SELECT * FROM customers";
    public static final String DELETE_SQL = "DELETE FROM customers";
    public static final String DELETE_BY_ID = "DELETE FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static RowMapper<Customer> customerRowMapper = (resultSet, i) -> new Customer.Builder()
            .customerId(toUUID(resultSet.getBytes("customer_id")))
            .name(resultSet.getString("name"))
            .email(resultSet.getString("email"))
            .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
            .lastLoginAt(resultSet.getTimestamp("last_login_at").toLocalDateTime())
            .build();

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("createdAt", customer.getCreatedAt() != null ? Timestamp.valueOf(customer.getCreatedAt()) : null);
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        }};
    }

    @Override
    public Customer insert(Customer customer) {
        int update = jdbcTemplate.update(INSERT_SQL, toParamMap(customer));
        if (update != 1) {
            logger.error("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public Optional<Customer> update(Customer customer) {
        int update = jdbcTemplate.update(UPDATE_SQL, toParamMap(customer));
        if (update != 1) {
            logger.error("Nothing was updated");
        }
        return Optional.of(customer);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            SELECT_BY_ID_SQL,
                            Collections.singletonMap("customerId", customerId.toString().getBytes()),
                            customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("got empty result");
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL, customerRowMapper);
    }

    @Override
    public int deleteAll() {
        int update = jdbcTemplate.update(DELETE_SQL, Collections.emptyMap());
        if (update != 1) {
            logger.error("Nothing was deleted");
        }
        return update;
    }

    @Override
    public int deleteById(UUID customerId) {
        int deleted = jdbcTemplate.update(DELETE_BY_ID, Map.of("customerId", customerId.toString().getBytes()));
        if (deleted != 1) {
            logger.error("Nothing was deleted");
        }
        return deleted;
    }
}
