package com.prgrms.custoemer.repository;

import com.prgrms.common.exception.UpdateException;
import com.prgrms.custoemer.model.Customer;
import com.prgrms.custoemer.model.Name;
import com.prgrms.common.exception.InsertException;
import com.prgrms.common.exception.UserNotFoundException;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Customer customer) {
        Map<String, Object> map = new HashMap<>();
        map.put("customer_id", customer.getCustomerId());
        map.put("name", customer.getName().getValue());
        map.put("email", customer.getEmail());
        map.put("created_at", Timestamp.valueOf(customer.getCreatedAt()));
        map.put("last_login_at",
                customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt())
                        : null);

        return map;
    }

    @Override
    public Customer insert(Customer customer) {
        int update = jdbcTemplate.update(
                "INSERT INTO customers(customer_id, name, email, created_at) VALUES (:customer_id, :name, :email, :created_at)",
                toParamMap(customer));
        if (update != 1) {
            throw new InsertException("고객 아이디 : " + customer.getCustomerId() );
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        int update = jdbcTemplate.update(
                "UPDATE customers SET name = :name, email = :email, last_login_at = :last_login_at WHERE customer_id = :customer_id",
                toParamMap(customer));
        if (update != 1) {
            throw new UpdateException("고객 아이디 : " + customer.getCustomerId());
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(
                "select last_login_at, customer_id, name, email, created_at from customers",
                getCustomerRowMapper());
    }

    public Optional<Customer> findById(String customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "select last_login_at, customer_id, name, email, created_at from customers WHERE customer_id = :customer_id",
                    Collections.singletonMap("customer_id", customerId),
                    getCustomerRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("아이디 : " + customerId);
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "select last_login_at, customer_id, name, email, created_at from customers WHERE name = :name",
                    Collections.singletonMap("name", name),
                    getCustomerRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("이름 : " + name);
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "select last_login_at, customer_id, name, email, created_at from customers WHERE email = :email",
                    Collections.singletonMap("email", email),
                    getCustomerRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("이메일 : " + email);
        }
    }

    @Override
    public boolean existsById(String customer_id) {

        return jdbcTemplate.queryForObject(
               "SELECT 1 " +
                "FROM customers "+
                "WHERE EXISTS (SELECT 1 FROM customers WHERE customer_id = :customer_id)"
                , Collections.singletonMap("customer_id"
                        , customer_id), Boolean.class);
    }

    public RowMapper<Customer> getCustomerRowMapper() {
        return (resultSet, i) -> {
            String customerId = resultSet.getString("customer_id");
            Name customerName = new Name(resultSet.getString("name"));
            String email = resultSet.getString("email");
            LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                    resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
            LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
            return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
        };
    }

}
