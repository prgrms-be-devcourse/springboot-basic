package com.example.kdtspringmission.customer.infra;

import com.example.kdtspringmission.customer.domain.Customer;
import com.example.kdtspringmission.customer.domain.CustomerRepository;
import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<Customer> customerRowMapper = new RowMapper<Customer>() {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            UUID customerId = toUUID(rs.getBytes("customer_id"));
            String customerName = rs.getString("name");
            String email = rs.getString("email");
            LocalDateTime lastLoginAt = rs.getTimestamp("last_login_at") == null ? null
                : rs.getTimestamp("last_login_at").toLocalDateTime();
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
        }
    };

    public CustomerJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer insert(Customer customer) {
        int update = jdbcTemplate.update(
            "insert into customers(customer_id, name, email, created_at) values (uuid_to_bin(?), ?, ?, ?)",
            customer.getCustomerId().toString().getBytes(),
            customer.getName(),
            customer.getEmail(),
            Timestamp.valueOf(customer.getCreatedAt()));

        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        int update = jdbcTemplate.update(
            "update customers set name = ?, email = ?, last_login_at = ? where customer_id = uuid_to_bin(?)",
            customer.getName(),
            customer.getEmail(),
            customer.getLastLoginAt() == null ? null : Timestamp.valueOf(customer.getLastLoginAt()),
            customer.getCustomerId().toString().getBytes());

        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", customerRowMapper);
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from customers", Integer.class);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate
                .queryForObject("select * from customers where customer_id = uuid_to_bin(?)",
                    customerRowMapper, customerId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty resultSet", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate
                .queryForObject("select * from customers where name = ?", customerRowMapper, name));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty resultSet", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate
                .queryForObject("select * from customers where email = ?", customerRowMapper, email));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty resultSet", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from customers");
    }

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
