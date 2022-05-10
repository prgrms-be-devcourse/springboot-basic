package com.prgrms.vouchermanagement.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.prgrms.vouchermanagement.customer.CustomerSql.*;

@Repository
public class CustomerNamedJdbcRepository implements CustomerRepository {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerNamedJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Customer customer)  throws DataAccessException {
        try {
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(INSERT_SQL, getCustomerParameterSource(customer), keyHolder, new String[]{"customer_id"});
            return keyHolder.getKey().longValue();
        } catch (DataAccessException e) {
            log.error("fail to execute query", e);
            throw e;
        }
    }

    @Override
    public void update(Customer customer) throws DataAccessException {
        try {
            jdbcTemplate.update(UPDATE_SQL, getCustomerParameterSource(customer));
        } catch (DataAccessException e) {
            log.error("fail to execute query", e);
            throw e;
        }
    }

    @Override
    public List<Customer> findAll() throws DataAccessException {
        try {
            return jdbcTemplate.query(SELECT_SQL, customerRowMapper);
        } catch (DataAccessException e) {
            log.error("fail to execute query", e);
            throw e;
        }
    }

    @Override
    public Optional<Customer> findById(Long customerID) throws DataAccessException {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID,
                    Collections.singletonMap("customerId", customerID), customerRowMapper));
        } catch (EmptyResultDataAccessException e) { //조회 결과가 0개인 경우
            return Optional.empty();
        } catch (DataAccessException e) {
            log.error("fail to execute query", e);
            throw e;
        }
    }

    @Override
    public List<Customer> findByName(String name) throws DataAccessException {
        try {
            return jdbcTemplate.query(SELECT_BY_NAME_SQL, Collections.singletonMap("name", name), customerRowMapper);
        } catch (DataAccessException e) {
            log.error("fail to execute query", e);
            throw e;
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) throws DataAccessException {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_EMAIL_SQL, Collections.singletonMap("email", email), customerRowMapper));
        } catch (EmptyResultDataAccessException e) { //조회 결과가 0개인 경우
            return Optional.empty();
        } catch (DataAccessException e) {
            log.error("fail to execute query", e);
            throw e;
        }
    }

    @Override
    public List<Customer> findCustomerByVoucher(Long voucherId) throws DataAccessException {
        try {
            return jdbcTemplate.query(FIND_CUSTOMER_BY_VOUCHER_SQL, Collections.singletonMap("voucherId", voucherId), customerRowMapper);
        } catch (DataAccessException e) {
            log.error("fail to execute query", e);
            throw e;
        }
    }

    @Override
    public void remove(Long customerId) throws DataAccessException {
        jdbcTemplate.update(DELETE_BY_ID_SQL, Collections.singletonMap("customerId", customerId));
    }

    private final RowMapper<Customer> customerRowMapper = (rs, rowNum) -> {
        long customerId = rs.getLong("customer_id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        LocalDateTime lastLoginAt = rs.getTimestamp("last_login_at") != null ? rs.getTimestamp("last_login_at").toLocalDateTime() : null;
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        return Customer.of(customerId, name, email, lastLoginAt, createdAt);
    };

    private MapSqlParameterSource getCustomerParameterSource(Customer customer) {
        return new MapSqlParameterSource()
                .addValue("customerId", customer.getCustomerId())
                .addValue("name", customer.getName())
                .addValue("email", customer.getEmail())
                .addValue("lastLoginAt", customer.getLastLoginAt())
                .addValue("createdAt", customer.getCreatedAt());
    }

}
