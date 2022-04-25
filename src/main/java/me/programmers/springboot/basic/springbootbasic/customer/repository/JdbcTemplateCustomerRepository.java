package me.programmers.springboot.basic.springbootbasic.customer.repository;

import me.programmers.springboot.basic.springbootbasic.customer.model.Customer;
import me.programmers.springboot.basic.springbootbasic.customer.model.CustomerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcTemplateCustomerRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcTemplateCustomerRepository.class);

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateCustomerRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    private static RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Customer(customerId, new CustomerInfo(customerName, email), lastLoginAt, createdAt);
    };

    @Override
    public Customer insert(Customer customer) {
        var update = jdbcTemplate.update(
                "insert into customers(customer_id, name, email, last_login_at, created_at) " +
                        "values (uuid_to_bin(?), ?, ?, ?, ?)",
                customer.getCustomerId().toString().getBytes(),
                customer.getCustomerInfo().getName(),
                customer.getCustomerInfo().getEmail(),
                Timestamp.valueOf(customer.getCreatedAt()),
                Timestamp.valueOf(customer.getCreatedAt()));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        var update = jdbcTemplate.update(
                "update customers set name = ?, email = ?, last_login_at = ? " +
                        "where customer_id = (uuid_to_bin(?))",
                customer.getCustomerInfo().getName(),
                customer.getCustomerInfo().getEmail(),
                Timestamp.valueOf(customer.getCreatedAt()),
                customer.getCustomerId().toString().getBytes());
        if (update != 1) {
            throw new IllegalArgumentException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(
                "select * from customers",
                customerRowMapper);
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(
                "select count(*) from customers",
                Integer.class);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(
                    "select * from customers where customer_id = (uuid_to_bin(?))",
                    customerRowMapper,
                    customerId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(
                    "select * from customers where name = ?",
                    customerRowMapper,
                    name));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(
                    "select * from customers where email = ?",
                    customerRowMapper,
                    email));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from customers");
    }

    private void mapToCustomer(List<Customer> allCustomers, ResultSet resultSet) throws SQLException {
        var customerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        var customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;

        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        allCustomers.add(new Customer(customerId, new CustomerInfo(customerName, email), lastLoginAt, createdAt));
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

}
