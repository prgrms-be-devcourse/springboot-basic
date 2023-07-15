package com.programmers.springmission.customer.repository;

import com.programmers.springmission.customer.domain.Customer;
import com.programmers.springmission.customer.domain.Wallet;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("jdbc")
public class JdbcCustomerRepository implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customerId = UUID.fromString(resultSet.getString("customer_id"));
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        return new Customer(customerId, name, email, createdAt);
    };

    private RowMapper<Wallet> walletRowMapper = (resultSet, i) -> {
        UUID customerId = UUID.fromString(resultSet.getString("customer_id"));
        UUID voucherId = UUID.fromString(resultSet.getString("voucher_id"));
        String voucherPolicy = resultSet.getString("policy");
        long voucherAmount = resultSet.getLong("amount");

        return new Wallet(customerId, voucherId, voucherPolicy, voucherAmount);
    };

    @Override
    public void save(Customer customer) {
        jdbcTemplate.update(
                "INSERT INTO customers (customer_id, name, email, created_at) VALUES (?, ?, ?, ?)",
                customer.getCustomerId().toString(),
                customer.getName(),
                customer.getEmail(),
                customer.getCreatedAt());
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT * FROM customers WHERE customer_id = ?",
                    customerRowMapper,
                    customerId.toString()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT * FROM customers where email = ?",
                    customerRowMapper,
                    email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM customers",
                customerRowMapper);
    }

    @Override
    public List<Wallet> findCustomerWallet(UUID customerId) {
        return jdbcTemplate.query(
                "SELECT v.* FROM customers c INNER JOIN vouchers v ON c.customer_id = v.customer_id WHERE v.customer_id = ?",
                walletRowMapper,
                customerId.toString());
    }

    @Override
    public void update(Customer customer) {
        jdbcTemplate.update(
                "UPDATE customers SET name = ? WHERE customer_id = ?",
                customer.getName(),
                customer.getCustomerId().toString());
    }

    @Override
    public void deleteById(UUID customerId) {
        jdbcTemplate.update(
                "DELETE FROM customers WHERE customer_id = ?",
                customerId.toString());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(
                "DELETE FROM customers");
    }
}
