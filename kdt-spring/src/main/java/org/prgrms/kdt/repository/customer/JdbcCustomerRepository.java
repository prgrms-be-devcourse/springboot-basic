package org.prgrms.kdt.repository.customer;

import org.prgrms.kdt.domain.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcCustomerRepository implements CustomerRepository{
    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);

    private final JdbcTemplate jdbcTemplate;

    private static RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        String customerName = resultSet.getString("name");
        UUID customerId = mapToUUID(resultSet.getBytes("customer_id"));
        String email = resultSet.getString("email");
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
    };

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer insert(Customer customer) {

        Assert.notNull(customer, "Customer should not be null");

        validDuplicateCustomer(customer);

        int insert = jdbcTemplate.update("INSERT INTO customers (customer_id, name, email, created_at) VALUES (UUID_TO_BIN(?), ?, ? , ?)",
                mapToBytes(customer.getCustomerId()),
                customer.getName(),
                customer.getEmail(),
                Timestamp.valueOf(customer.getCreatedAt()));

        if (insert != 1)
            throw new RuntimeException("Nothing was inserted");

        return customer;
    }



    @Override
    public Customer update(Customer customer) {

        Assert.notNull(customer, "Customer should not be null");

        int update = jdbcTemplate.update("UPDATE customers SET name = ?, email = ?, last_login_at = ? WHERE customer_id = UUID_TO_BIN(?)",
                customer.getName(),
                customer.getEmail(),
                customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null,
                mapToBytes(customer.getCustomerId()));

        if (update != 1)
            throw new RuntimeException("Nothing was updated");

        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customers", customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {

        Assert.notNull(customerId, "Customer id should not be null");

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(?)", customerRowMapper, mapToBytes(customerId)));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {

        Assert.notNull(name, "Customer name should not be null");

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE name = ?", customerRowMapper, name));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {

        Assert.notNull(email, "Customer email should not be null");

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE email = ?", customerRowMapper, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM customers", Integer.class);
    }


    @Transactional
    @Override
    public void delete(Customer customer) {
        Assert.notNull(customer, "Customer should not be null");

        deleteById(customer.getCustomerId());
    }

    @Transactional
    @Override
    public void deleteById(UUID customerId) {
        Assert.notNull(customerId, "Customer id should not be null");

        jdbcTemplate.update("DELETE FROM customers WHERE customer_id = UUID_TO_BIN(?)", mapToBytes(customerId));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM customers");
    }

    static UUID mapToUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private byte[] mapToBytes(UUID customerId) {
        return customerId.toString().getBytes();
    }

    private boolean exist(Customer customer) {
        return findByEmail(customer.getEmail()).isPresent();
    }

    private void validDuplicateCustomer(Customer customer) {
        if (exist(customer))
            throw new RuntimeException("Customer is already existed with email = " + customer.getEmail());
    }

}
