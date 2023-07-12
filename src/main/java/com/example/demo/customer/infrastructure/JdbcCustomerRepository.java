package com.example.demo.customer.infrastructure;

import com.example.demo.customer.domain.Customer;
import com.example.demo.customer.domain.repostiory.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Customer> rowMapper = (rs, rowNum) -> new Customer(
            toUUID(rs.getBytes("customer_id")),
            rs.getString("name"),
            rs.getString("email"),
            rs.getObject("last_login_at", LocalDateTime.class),
            rs.getObject("created_at", LocalDateTime.class)
    );

    @Override
    public Customer insert(Customer customer) {
        String sql = "INSERT INTO customers (customer_id, name, email, created_at) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, toBytes(customer.getCustomerId()), customer.getName(), customer.getEmail(), customer.getCreatedAt());
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        String sql = "UPDATE customers SET name = ?, last_login_at = ? WHERE customer_id = ?";
        jdbcTemplate.update(sql, customer.getName(), customer.getLastLoginAt(), customer.getCustomerId());
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM customers";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";
        return jdbcTemplate.query(sql, rowMapper, toBytes(customerId)).stream().findAny();
    }

    @Override
    public Optional<Customer> findByName(String name) {
        String sql = "SELECT * FROM customers WHERE name = ? LIMIT 1";
        return jdbcTemplate.query(sql, rowMapper, name).stream().findAny();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        String sql = "SELECT * FROM customers WHERE email = ?";
        return jdbcTemplate.query(sql, rowMapper, email).stream().findAny();
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM customers";
        jdbcTemplate.update(sql);
    }

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private static byte[] toBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }
}
