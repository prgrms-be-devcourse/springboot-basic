package com.prgrms.management.customer.repository;

import com.prgrms.management.config.ErrorMessageType;
import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.customer.domain.CustomerType;
import com.prgrms.management.util.ToUUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile({"jdbc", "test"})
public class JdbcCustomerRepository implements CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerId = ToUUID.toUUId(resultSet.getBytes("customer_id"));
        var customerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var customerType = CustomerType.of(resultSet.getString("customer_type"));
        return new Customer(customerId, customerName, email, createdAt, customerType);
    };

    @Override
    public Customer save(Customer customer) {
        int update = jdbcTemplate.update("insert into customers(customer_id, name, email, created_at, customer_type) values (UUID_TO_BIN(?), ?, ?, ?, ?)",
                customer.getCustomerId().toString().getBytes(),
                customer.getName(),
                customer.getEmail(),
                Timestamp.valueOf(customer.getCreatedAt()),
                customer.getCustomerType().equals(CustomerType.NORMAL) ? "normal" : "blacklist");
        if (update != 1) {
            throw new IllegalStateException(ErrorMessageType.NOT_EXECUTE_QUERY.getMessage());
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", customerRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from customers");
    }

    @Override
    public void deleteById(UUID customerId) {
        jdbcTemplate.update("delete from customers where customer_id = UUID_TO_BIN(?)", customerId.toString().getBytes());
    }

    @Override
    public Customer updateName(Customer customer) {
        int update = jdbcTemplate.update("update customers set name = ? where customer_id = UUID_TO_BIN(?)",
                customer.getName(),
                customer.getCustomerId().toString().getBytes()
        );
        if (update != 1) {
            throw new IllegalStateException(ErrorMessageType.NOT_EXECUTE_QUERY.getMessage());
        }
        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "select * from customers where customer_id = UUID_TO_BIN(?)",
                    customerRowMapper,
                    customerId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.info("{}:{}", e.getClass(), e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "select * from customers where email = ?",
                    customerRowMapper,
                    email));
        } catch (EmptyResultDataAccessException e) {
            logger.info("{}:{}", e.getClass(), e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findBlackList() {
        return jdbcTemplate.query("select * from customers where customer_type = ? ",
                customerRowMapper,
                "blacklist");
    }
}
