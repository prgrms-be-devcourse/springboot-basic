package com.programmers.customer.repository;

import com.programmers.customer.Customer;
import com.programmers.customer.repository.sql.CustomerResultSetExtractor;
import com.programmers.customer.repository.sql.CustomerRowMapper;
import com.programmers.voucher.repository.sql.VoucherRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
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
public class DbCustomerRepository implements CustomerRepository {
    public static final String CUSTOMER_ID = "customerId";
    public static final String CUSTOMER_NAME = "name";
    public static final String CUSTOMER_EMAIL = "email";
    public static final String CREATE_AT = "createAt";
    public static final String LAST_LOGIN_AT = "lastLoginAt";
    private final Logger log = LoggerFactory.getLogger(DbCustomerRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;
    private final VoucherRowMapper voucherRowMapper;
    private final CustomerResultSetExtractor resultSetExtractor;

    public DbCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.voucherRowMapper = new VoucherRowMapper();
        this.customerRowMapper = new CustomerRowMapper();
        this.resultSetExtractor = new CustomerResultSetExtractor(customerRowMapper, voucherRowMapper);
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
            throw new RuntimeException(DB_ERROR_LOG.getMessage());
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(
                                SELECT_BY_EMAIL,
                                singletonMap(CUSTOMER_EMAIL, email),
                                customerRowMapper
                        )
                ));

    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.query(
                            SELECT_ALL_BY_ID,
                            singletonMap(CUSTOMER_ID, customerId.toString().getBytes()),
                            resultSetExtractor
                    )
            );

        } catch (DataAccessException e) {
            log.error(DB_ERROR_LOG.getMessage(), e);
            throw new RuntimeException(DB_ERROR_LOG.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL, emptyMap());
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(SELECT_COUNT, emptyMap(), Integer.class);
    }

    @Override
    public Customer insert(Customer customer) {
        try {
            jdbcTemplate.update(INSERT_CUSTOMER, toParamMap(customer));
        } catch (DataAccessException e) {
            log.error(DB_ERROR_LOG.getMessage());
            throw new RuntimeException(INSERT_ERROR.getMessage());
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        try {
            jdbcTemplate.update(UPDATE_CUSTOMER, toParamMap(customer));
        } catch (DataAccessException e) {
            log.error(DB_ERROR_LOG.getMessage(), e);
            throw new RuntimeException(DB_ERROR_LOG.getMessage());
        }

        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL, customerRowMapper);
    }

    @Override
    public void deleteCustomer(UUID customerId) {
        jdbcTemplate.update(DELETE_CUSTOMER, Collections.singletonMap(CUSTOMER_ID, customerId.toString().getBytes()));
    }

    private Map<String, Object> toParamMap(Customer customer) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(CUSTOMER_NAME, customer.getName());
        paramMap.put(CUSTOMER_EMAIL, customer.getEmail());
        paramMap.put(CREATE_AT, customer.getCreateAt());
        paramMap.put(LAST_LOGIN_AT, customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        paramMap.put(CUSTOMER_ID, customer.getCustomerId().toString().getBytes());

        return paramMap;
    }
}
