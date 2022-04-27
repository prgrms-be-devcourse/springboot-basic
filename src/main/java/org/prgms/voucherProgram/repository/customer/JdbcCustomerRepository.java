package org.prgms.voucherProgram.repository.customer;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.prgms.voucherProgram.domain.customer.Customer;
import org.prgms.voucherProgram.utils.DatabaseUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer save(Customer customer) {
        int result = jdbcTemplate.update(
            "INSERT INTO customer(customer_id, name, email, created_at) VALUES(UUID_TO_BIN(?), ?, ?, ?)",
            DatabaseUtils.toBytes(customer.getCustomerId()),
            customer.getName(),
            customer.getEmail(),
            Timestamp.valueOf(customer.getCreatedTime()));

        DatabaseUtils.validateExecute(result);
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        int result = jdbcTemplate.update(
            "UPDATE customer SET name = ?, email = ?, last_login_at = ? WHERE customer_id = UUID_TO_BIN(?)",
            customer.getName(),
            customer.getEmail(),
            customer.getLastLoginTime() != null ? Timestamp.valueOf(customer.getLastLoginTime()) : null,
            DatabaseUtils.toBytes(customer.getCustomerId()));

        DatabaseUtils.validateExecute(result);
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customer", DatabaseUtils.customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject("SELECT * FROM customer WHERE customer_id = UUID_TO_BIN(?)",
                    DatabaseUtils.customerRowMapper
                    , DatabaseUtils.toBytes(customerId)));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customer WHERE email = ?",
                DatabaseUtils.customerRowMapper, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByVoucherId(UUID voucherId) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                    "SELECT * FROM customer c JOIN voucher v on c.customer_id = v.customer_id WHERE voucher_id = UUID_TO_BIN(?)",
                    DatabaseUtils.customerRowMapper, voucherId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM customer");
    }

    @Override
    public void deleteById(UUID customerId) {
        int result = jdbcTemplate.update("DELETE FROM customer WHERE customer_id = UUID_TO_BIN(?)",
            DatabaseUtils.toBytes(customerId));
        DatabaseUtils.validateExecute(result);
    }

    @Override
    public void deleteByEmail(String email) {
        int result = jdbcTemplate.update("DELETE FROM customer WHERE email = ?", email);
        DatabaseUtils.validateExecute(result);
    }
}

