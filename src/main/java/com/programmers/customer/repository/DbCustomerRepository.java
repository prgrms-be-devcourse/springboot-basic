package com.programmers.customer.repository;

import com.programmers.customer.Customer;
import com.programmers.customer.repository.sql.CustomerRowMapper;
import com.programmers.customer.repository.sql.CustomerSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

import static com.programmers.customer.repository.sql.CustomerSql.*;
import static com.programmers.message.ErrorMessage.DB_ERROR_LOG;
import static com.programmers.message.ErrorMessage.INSERT_ERROR;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

@Profile("dev")
@Repository
public class DbCustomerRepository implements CustomerRepository{
    private final Logger log = LoggerFactory.getLogger(DbCustomerRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public DbCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = new CustomerRowMapper();
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(CustomerSql.SELECT_BY_ID
                            , singletonMap("customerId", customerId.toString().getBytes()), customerRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            log.error(DB_ERROR_LOG.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_NAME,
                    singletonMap("name", name),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.error(DB_ERROR_LOG.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_EMAIL,
                    singletonMap("email", email),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.error(DB_ERROR_LOG.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL , emptyMap());
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(SELECT_COUNT , emptyMap(), Integer.class);
    }

    @Override
    public Customer insert(Customer customer) {
        int count = jdbcTemplate.update(INSERT_CUSTOMER, toParamMap(customer));

        if (count != 1) {
            log.error(DB_ERROR_LOG.getMessage());
            throw new RuntimeException(INSERT_ERROR.getMessage());
        }

        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        int update = jdbcTemplate.update(
                UPDATE_CUSTOMER,
                toParamMap(customer));

        if (update != 1) {
            log.error(DB_ERROR_LOG.getMessage());
            throw new RuntimeException();
        }

        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL , customerRowMapper);
    }

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("name",customer.getName());
            put("email",customer.getEmail());
            put("createAt", customer.getCreateAt());
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
            put("customerId", customer.getCustomerId().toString().getBytes() );
        }};
    }
}
