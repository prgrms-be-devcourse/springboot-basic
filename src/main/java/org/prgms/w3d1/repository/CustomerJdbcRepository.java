package org.prgms.w3d1.repository;

import org.prgms.w3d1.model.customer.Customer;
import org.prgms.w3d1.util.Util;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("dev")
public class CustomerJdbcRepository implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public CustomerJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Customer> rowMapper = (rs, rowNum) -> {
        var customerId = Util.toUUID(rs.getBytes("customer_id"));
        var name = rs.getString("name");
        var email = rs.getString("email");
        var lastLoginAt = rs.getTimestamp("last_login_at") != null ?
            rs.getTimestamp("last_login_at").toLocalDateTime() : null;
        var createAt = rs.getTimestamp("create_at").toLocalDateTime();

        return new Customer(customerId, name, email, lastLoginAt, createAt);
    };

    @Override
    public Customer insert(Customer customer) {
        var insertCount = jdbcTemplate.update(
            "insert into customers(customer_id, name, email, create_at) values(UUID_TO_BIN(?), ?, ?, ?)",
            customer.getCustomerId().toString().getBytes(),
            customer.getName(),
            customer.getEmail(),
            Timestamp.valueOf(customer.getCreatedAt()));

        if (insertCount != 1) {
            throw new RuntimeException("Nothing was inserted");
        }

        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        var updateCount = jdbcTemplate.update(
            "update customers set name=?, email=?, last_login_at=? where customer_id=UUID_TO_BIN(?)",
            customer.getName(),
            customer.getEmail(),
            customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null,
            customer.getCustomerId().toString().getBytes());

        if (updateCount != 1) {
            throw new RuntimeException("Nothing was updated");
        }

        return customer;
    }

    @Override
    public Customer updateWithNameAndEmail(UUID customerId, String name, String email) {
        var updateCount = jdbcTemplate.update(
            "update customers set name=?, email=? where customer_id=UUID_TO_BIN(?)",
            name, email, customerId.toString().getBytes());

        if (updateCount != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        var updatedCustomer = findById(customerId);
        if (updatedCustomer.isEmpty()) {
            throw new RuntimeException("There is no Customer : " + customerId);
        }

        return updatedCustomer.get();
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", rowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                    "select * from customers where customer_id = UUID_TO_BIN(?)",
                    rowMapper, customerId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                    "select * from customers where name = ?",
                    rowMapper, name));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                    "select * from customers where email = ?",
                    rowMapper, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findByCreatedDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return jdbcTemplate.query("select * from customers where create_at between ? and ?", rowMapper,
            Timestamp.valueOf(startDate),
            Timestamp.valueOf(endDate));
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from customers");
    }

    @Override
    public void deleteById(UUID customerId) {
        var deleteCount = jdbcTemplate.update("delete from customers where customer_id = UUID_TO_BIN(?)",
            customerId.toString().getBytes());

        if (deleteCount != 1) {
            throw new RuntimeException("Nothing was deleted");
        }
    }
}
