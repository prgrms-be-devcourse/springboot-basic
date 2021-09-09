package org.prgrms.kdt.kdtspringorder.custommer.repository;

import org.prgrms.kdt.kdtspringorder.common.exception.CustomerNotFoundException;
import org.prgrms.kdt.kdtspringorder.common.util.UuidUtil;
import org.prgrms.kdt.kdtspringorder.custommer.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Profile({"dev", "default"})
@Repository
public class CustomerJdbcRepository implements CustomerRepository {

    private final String SELECT_ALL_SQL = "select * from customers";
    private final String SELECT_BY_ID_SQL = "select * from customers where customer_id = UUID_TO_BIN(:customerId)";
    private final String SELECT_BY_NAME_SQL = "select * from customers where name = :name";
    private final String SELECT_BY_EMAIL_SQL = "select * from customers where email = :email";
    private final String INSERT_SQL = "INSERT INTO customers(customer_id, name, email, created_at) VALUES(UUID_TO_BIN(:customerId), :name, :email, :createdAt)";
    private final String UPDATE_SQL = "UPDATE customers SET name = :name, email = :email, last_login_at = :lastLoginAt WHERE customer_id = UUID_TO_BIN(:customerId)";
    private final String DELETE_ALL_SQL = "DELETE FROM customers";
    private final String DELETE_SQL = "DELETE FROM customers WHERE customer_id = UUID_TO_BIN(:customerId) ";

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from customers", Map.of(), Integer.class);
    }

    @Override
    public UUID insert(Customer customer) {
        final int update = jdbcTemplate.update(INSERT_SQL, toParamMap(customer));
        if (update != 1) {
            throw new CustomerNotFoundException(customer.getCustomerId());
        }
        return customer.getCustomerId();
    }

    @Override
    public UUID update(Customer customer) {
        final int update = jdbcTemplate.update(UPDATE_SQL, toParamMap(customer));

        if (update != 1) {
            throw new CustomerNotFoundException(customer.getCustomerId());
        }

        return customer.getCustomerId();
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, CustomerJdbcRepository::customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, Collections.singletonMap("customerId", customerId.toString()), CustomerJdbcRepository::customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                    SELECT_BY_NAME_SQL,
                    Collections.singletonMap("name", name),
                    CustomerJdbcRepository::customerRowMapper
                )
            );
        } catch (EmptyResultDataAccessException e) {
            logger.error("empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByIEmail(String email) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                    SELECT_BY_EMAIL_SQL,
                    Collections.singletonMap("email", email),
                    CustomerJdbcRepository::customerRowMapper
                )
            );
        } catch (EmptyResultDataAccessException e) {
            logger.error("empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public int deleteAll() {
        final int update = jdbcTemplate.update(DELETE_ALL_SQL, Map.of());
        return update;
    }

    @Override
    public int delete(UUID customerId) {
        final int update = jdbcTemplate.update(DELETE_SQL, Map.of("customerId", customerId.toString()));

        if (update != 1) {
            throw new CustomerNotFoundException(customerId);
        }

        return update;
    }

    private HashMap<String, Object> toParamMap(Customer customer) {
        final String customerId = customer.getCustomerId().toString();
        final String name = customer.getName();
        final String email = customer.getEmail();
        final Timestamp createdAt = Timestamp.valueOf(customer.getCreatedAt());
        final Timestamp lastLoginAt = customer.getLastLoginAt() != null
            ? Timestamp.valueOf(customer.getLastLoginAt()) : null;
        final HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerId", customerId);
        paramMap.put("name", name);
        paramMap.put("email", email);
        paramMap.put("createdAt", createdAt);
        paramMap.put("lastLoginAt", lastLoginAt);
        return paramMap;
    }

    private static Customer customerRowMapper(ResultSet resultSet, int i) throws SQLException {
        final UUID customerId = UuidUtil.toUUID(resultSet.getBytes("customer_id"));
        final String customerName = resultSet.getString("name");
        final String email = resultSet.getString("email");
        final LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null
            ? resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        final LocalDateTime createdAt = resultSet.getTimestamp("created_at") != null
            ? resultSet.getTimestamp("created_at").toLocalDateTime() : null;
        return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
    }

}
