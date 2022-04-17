package org.prgrms.vouchermanager.customer.repository;

import org.prgrms.vouchermanager.customer.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerJDBCRepository implements CustomerRepository {

    private final String INSERT_CUSTOMER = "INSERT INTO customers(customer_id, name, email, create_at) VALUES (UUID_TO_BIN(?), ?, ?, ?)";
    private final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(?)";
    private final Logger log = LoggerFactory.getLogger(CustomerJDBCRepository.class);

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customer_id = customerIdBytesToUUID(resultSet.getBytes("customer_id"));
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") == null ? null : resultSet.getTimestamp("last_login_at").toLocalDateTime();
        LocalDateTime createdAt = resultSet.getTimestamp("create_at").toLocalDateTime();
        return new Customer(customer_id, name, email, createdAt, lastLoginAt);
    };

    public CustomerJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private UUID customerIdBytesToUUID(byte[] customer_ids) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(customer_ids);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public Customer insert(Customer customer) {
        int theNumberOfRowAffected = jdbcTemplate.update(INSERT_CUSTOMER,
                customer.getCustomerId().toString().getBytes(StandardCharsets.UTF_8),
                customer.getName(),
                customer.getEmail(),
                Timestamp.valueOf(customer.getCreateAt()));
        if (theNumberOfRowAffected != 1)
            throw new IllegalArgumentException("잘못된 삽입");
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_CUSTOMER_BY_ID, customerRowMapper, customerId.toString().getBytes(StandardCharsets.UTF_8)));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(UUID customerId) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByEmail(UUID customerId) {
        return Optional.empty();
    }

    @Override
    public void delete(UUID customerId) {

    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM customers");
    }
}
