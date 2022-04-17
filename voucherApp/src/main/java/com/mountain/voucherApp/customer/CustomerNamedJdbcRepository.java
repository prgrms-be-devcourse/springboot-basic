package com.mountain.voucherApp.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static com.mountain.voucherApp.constants.Field.*;
import static com.mountain.voucherApp.constants.Message.*;
import static com.mountain.voucherApp.constants.Query.*;
import static com.mountain.voucherApp.utils.CommonUtil.toUUID;

@Repository
public class CustomerNamedJdbcRepository implements CustomerRepository {

    private static final Logger log = LoggerFactory.getLogger(CustomerNamedJdbcRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerNamedJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Customer customer) {
        Map<String, Object> paramMap = new HashMap<>() {{
            put(CUSTOMER_ID_CAMEL, customer.getCustomerId().toString().getBytes(StandardCharsets.UTF_8));
            put(VOUCHER_ID_CAMEL, customer.getVoucherId() != null ? customer.getVoucherId().toString().getBytes(StandardCharsets.UTF_8) : null);
            put(NAME, customer.getName());
            put(EMAIL, customer.getEmail());
            put(CREATED_AT_CAMEL, Timestamp.valueOf(customer.getCreatedAt()));
            put(LAST_LOGIN_AT_CAMEL, customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        }};
        return paramMap;
    }

    @Override
    public Customer insert(Customer customer) {
        Map paramMap = toParamMap(customer);
        int executeUpdate = jdbcTemplate.update(
                INSERT_CUSTOMER,
                paramMap
        );
        if (executeUpdate != EXECUTE_SUCCESS) {
            throw new RuntimeException(NOT_INSERTED);
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        Map paramMap = toParamMap(customer);
        int executeUpdate = jdbcTemplate.update(
                UPDATE_CUSTOMER,
                paramMap
        );
        if (executeUpdate != EXECUTE_SUCCESS) {
            throw new RuntimeException(NOT_UPDATED);
        }
        return customer;
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(
                COUNT_CUSTOMER,
                Collections.emptyMap(),
                Integer.class
        );
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL_CUSTOMER, customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    SELECT_CUSTOMER_BY_ID,
                    Collections.singletonMap(CUSTOMER_ID_CAMEL, customerId.toString().getBytes()),
                    customerRowMapper
            ));
        } catch (EmptyResultDataAccessException e) {
            log.error(EMPTY_RESULT_ERROR, e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    SELECT_CUSTOMER_BY_NAME,
                    Collections.singletonMap(NAME, name),
                    customerRowMapper
            ));
        } catch (EmptyResultDataAccessException e) {
            log.error(EMPTY_RESULT_ERROR, e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    SELECT_CUSTOMER_BY_EMAIL,
                    Collections.singletonMap(EMAIL, email),
                    customerRowMapper
            ));
        } catch (EmptyResultDataAccessException e) {
            log.error(EMPTY_RESULT_ERROR, e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_CUSTOMER, Collections.emptyMap());
    }

    private static RowMapper<Customer> customerRowMapper = new RowMapper<Customer>() {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            String customerName = rs.getString(NAME);
            byte[] voucherId = rs.getBytes(VOUCHER_ID);
            String email = rs.getString(EMAIL);
            byte[] customerId = rs.getBytes(CUSTOMER_ID);
            LocalDateTime lastLoginAt = rs.getTimestamp(LAST_LOGIN_AT) != null ?
                    rs.getTimestamp(LAST_LOGIN_AT).toLocalDateTime() : null;
            LocalDateTime createdAt = rs.getTimestamp(CREATED_AT).toLocalDateTime();
            UUID customerUUID = toUUID(customerId);
            UUID voucherUUID = voucherId != null ? toUUID(voucherId) : null;
            lastLoginAt = (lastLoginAt != null) ? lastLoginAt : null;
            return new Customer(customerUUID, voucherUUID, customerName, email, lastLoginAt, createdAt);
        }
    };

}
