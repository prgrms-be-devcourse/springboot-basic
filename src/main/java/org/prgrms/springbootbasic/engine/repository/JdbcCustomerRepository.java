package org.prgrms.springbootbasic.engine.repository;

import org.prgrms.springbootbasic.engine.domain.Customer;
import org.prgrms.springbootbasic.engine.enumtype.ErrorCode;
import org.prgrms.springbootbasic.exception.RecordNotUpdatedException;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static org.prgrms.springbootbasic.engine.util.JdbcUtil.toUUID;

@Repository
@Profile({"test", "default"})
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Customer> customerRowMapper = (resultSet, rowNum) -> {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        Boolean isBlack = resultSet.getBoolean("is_black");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ? resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
    };

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<String, Object>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("isBlack", customer.getIsBlack());
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        }};
    }

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", customerRowMapper);
    }

    @Override
    public List<Customer> findBlackStatus(Boolean isBlack) {
        return jdbcTemplate.query("select * from customers where is_black=:isBlack", Collections.singletonMap("isBlack", isBlack), customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers where customer_id=UNHEX(REPLACE(:customerId, '-', ''));", Collections.singletonMap("customerId", customerId.toString().getBytes()), customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers where name=:name;", Collections.singletonMap("name", name), customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers where email=:email;", Collections.singletonMap("email", email), customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Customer insert(Customer customer) {
        Map<String, Object> paramMap = toParamMap(customer);
        int update = jdbcTemplate.update("insert into customers(customer_id, name, is_black, email, created_at) values (UNHEX(REPLACE(:customerId, '-', '')), :name, :isBlack, :email, :createdAt);", paramMap);
        if (update != 1) {
            throw new RecordNotUpdatedException("Customer cant be inserted!", ErrorCode.CUSTOMER_NOT_UPDATED);
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        Map<String, Object> paramMap = toParamMap(customer);
        int update = jdbcTemplate.update("update customers set name = :name, is_black = :isBlack, last_login_at = :lastLoginAt where customer_id = UNHEX(REPLACE(:customerId, '-', ''));", paramMap);
        if (update < 1) {
            throw new RecordNotUpdatedException("Customer cant be updated!", ErrorCode.CUSTOMER_NOT_UPDATED);
        }
        return customer;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from customers;", Collections.emptyMap());
    }

    @Override
    public void deleteById(UUID customerId) {
        int deleteCount = jdbcTemplate.update("delete from customers where customer_id=UNHEX(REPLACE(:customerId, '-', ''))", Collections.singletonMap("customerId", customerId.toString().getBytes()));
        if (deleteCount != 1) {
            throw new RecordNotUpdatedException("Customer cant be deleted!", ErrorCode.CUSTOMER_NOT_UPDATED);
        }
    }
}
