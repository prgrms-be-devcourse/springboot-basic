package org.prgrms.kdt.repository.customer;

import com.zaxxer.hikari.HikariDataSource;
import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.customer.RegularCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile({"prod", "default"})
public class RegularCustomerRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(RegularCustomerRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customerId = toUUID(resultSet);
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        Integer isBadCustomer = resultSet.getInt("isBadCustomer");
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new RegularCustomer(customerId, customerName, email, isBadCustomer, lastLoginAt, createdAt);
    };

    public RegularCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<String, Object>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("isBadCustomer", customer.getBadCustomer());
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        }};
    }

    @Override
    public Customer insert(Customer customer) {
        int update = jdbcTemplate.update("INSERT INTO customers(customer_id, name, email, last_login_at, created_at, isBadCustomer) VALUES (UUID_TO_BIN(:customerId), :name, :email, :lastLoginAt, :createdAt, :isBadCustomer)", toParamMap(customer));
        if(update != 1){
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        int update = jdbcTemplate.update("UPDATE customers SET name = :name, email = :email, isBadCustomer = :isBadCustomer, last_login_at = :lastLoginAt WHERE customer_id = UUID_TO_BIN(:customerId)", toParamMap(customer));
        if(update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customers", customerRowMapper);
    }

    @Override
    public List<Customer> findAllBadCustomer() {
        return jdbcTemplate.query("SELECT * FROM customers WHERE isBadCustomer = '1'", customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE name = :name",
                    Collections.singletonMap("name", name),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE email = :email",
                    Collections.singletonMap("email", email),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM customers", Collections.emptyMap());
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM customers", Collections.emptyMap(), Integer.class);
    }

    static private UUID toUUID(ResultSet resultSet) throws SQLException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(resultSet.getBytes("customer_id"));
        UUID customerId = new UUID(byteBuffer.getLong(), byteBuffer.getLong());
        return customerId;
    }
}