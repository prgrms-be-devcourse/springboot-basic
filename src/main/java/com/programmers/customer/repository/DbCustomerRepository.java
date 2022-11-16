package com.programmers.customer.repository;

import com.programmers.customer.Customer;
import com.programmers.customer.repository.sql.CustomerRowMapper;
import com.programmers.customer.repository.sql.CustomerSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
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

    public final static String CUSTOMER_ID = "customerId";
    public final static String CUSTOMER_NAME = "name";
    public final static String CUSTOMER_EMAIL = "email";
    public final static String CREATE_AT = "createAt";
    public final static String LAST_LOGIN_AT = "lastLoginAt";

    public DbCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = new CustomerRowMapper();
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            CustomerSql.SELECT_BY_ID,
                            singletonMap(CUSTOMER_ID, customerId.toString().getBytes()),
                            customerRowMapper
                    )
            );
        } catch (DataAccessException e) {
            log.error(DB_ERROR_LOG.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            SELECT_BY_NAME,
                            singletonMap(CUSTOMER_NAME, name),
                            customerRowMapper
                    )
            );
        } catch (DataAccessException e) {
            log.error(DB_ERROR_LOG.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            SELECT_BY_EMAIL,
                            singletonMap(CUSTOMER_EMAIL, email),
                            customerRowMapper
                    )
            );
        } catch (DataAccessException e) {
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
                toParamMap(customer)
        );

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
            put(CUSTOMER_NAME, customer.getName());
            put(CUSTOMER_EMAIL, customer.getEmail());
            put(CREATE_AT, customer.getCreateAt());
            put(LAST_LOGIN_AT, customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
            put(CUSTOMER_ID, customer.getCustomerId().toString().getBytes() );
        }};
    }
}
