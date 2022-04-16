package org.prgms.voucherProgram.repository.customer;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.prgms.voucherProgram.entity.customer.Customer;
import org.prgms.voucherProgram.exception.DuplicateEmailException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private static final RowMapper<Customer> customerRowMapper = (resultSet, rowNum) -> {
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var name = resultSet.getString("name");
        var email = resultSet.getString("email");
        var createdTime = resultSet.getTimestamp("created_at").toLocalDateTime();
        var lastLoginTime = resultSet.getTimestamp("last_login_at") != null ?
            resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        return new Customer(customerId, name, email, createdTime, lastLoginTime);
    };

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public Customer save(Customer customer) {
        try {
            jdbcTemplate.update(
                "INSERT INTO customers(customer_id, name, email, created_at) VALUES(UUID_TO_BIN(?), ?, ?, ?)",
                toBytes(customer.getCustomerId()),
                customer.getName(),
                customer.getEmail(),
                Timestamp.valueOf(customer.getCreatedTime()));
        } catch (DuplicateKeyException e) {
            throw new DuplicateEmailException();
        }
        return customer;
    }

    private byte[] toBytes(UUID customerId) {
        return customerId.toString().getBytes();
    }

    @Override
    public Customer update(Customer customer) {
        try {
            jdbcTemplate.update(
                "UPDATE customers SET name = ?, email = ?, last_login_at = ? WHERE customer_id = UUID_TO_BIN(?)",
                customer.getName(),
                customer.getEmail(),
                customer.getLastLoginTime() != null ? Timestamp.valueOf(customer.getLastLoginTime()) : null,
                toBytes(customer.getCustomerId()));
        } catch (DuplicateKeyException e) {
            throw new DuplicateEmailException();
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customers", customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject("SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(?)",
                    customerRowMapper, toBytes(customerId)));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE email = ?",
                customerRowMapper, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM customers");
    }

    @Override
    public void deleteById(UUID customerId) {

    }
}
