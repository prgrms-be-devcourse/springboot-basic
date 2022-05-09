package org.prgrms.kdt.shop.repository;

import org.prgrms.kdt.shop.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);
    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ? resultSet.getTimestamp(
            "last_login_at").toLocalDateTime() : null;
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
    };

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public CustomerJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Customer insert(Customer customer) {
        String sql = "insert into customers(customer_id, name, email, created_at) values(UUID_TO_BIN(?),?,?,?)";
        var insert = jdbcTemplate.update(sql, customer.getCustomerId().toString().getBytes(),
            customer.getName(), customer.getEmail(), Timestamp.valueOf(customer.getCreateAt()));
        if (insert != 1) {
            logger.error("Nothing was inserted.");
            throw new IllegalArgumentException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        String sql = "update customers set name = ?, email = ? ,last_login_at = ? WHERE customer_id = UUID_TO_BIN(?)";
        var update = jdbcTemplate.update(sql, customer.getName(), customer.getEmail(),
            customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null,
            customer.getCustomerId().toString().getBytes());
        if (update != 1) {
            logger.error("Nothing was updated");
            throw new IllegalArgumentException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public int count() {
        String sql = "select count(*) from customers";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public List<Customer> findAll() {
        String sql = "select * from customers";
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        String sql = "select * from customers WHERE customer_id = UUID_TO_BIN(?)";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, customerRowMapper,
                customerId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByName(String name) {
        String sql = "select * from customers WHERE name = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, customerRowMapper, name));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        String sql = "select * from customers WHERE email = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, customerRowMapper, email));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
        }
        return Optional.empty();
    }

    @Override
    public void delete(UUID customerId) {
        String sql = "delete from customers where customer_id = UUID_TO_BIN(?)";
        try {
            Optional.ofNullable(jdbcTemplate.update(sql, customerId.toString().getBytes()));

        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM customers");
    }
}
