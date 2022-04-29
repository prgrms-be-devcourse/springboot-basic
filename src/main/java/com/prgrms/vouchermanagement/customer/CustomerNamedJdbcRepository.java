package com.prgrms.vouchermanagement.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
    public void save(Customer customer)  throws DataAccessException {
        Map<String, Object> paramMap = customerToMap(customer);
        try {
            jdbcTemplate.update(INSERT_SQL, paramMap);
        } catch (DataAccessException e) {
            log.error("fail to execute query", e);
            throw e;
        }
    }

    @Override
    public void update(Customer customer) throws DataAccessException {
        Map<String, Object> paramMap = customerToMap(customer);
        try {
            jdbcTemplate.update(UPDATE_SQL, paramMap);
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
    public Optional<Customer> findById(UUID customerID) throws DataAccessException {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID,
                    Collections.singletonMap("customerId", customerID.toString()), customerRowMapper));
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
    public List<Customer> findCustomerByVoucher(UUID voucherId) throws DataAccessException {
        try {
            return jdbcTemplate.query(FIND_CUSTOMER_BY_VOUCHER_SQL, Collections.singletonMap("voucherId", voucherId.toString()), customerRowMapper);
        } catch (DataAccessException e) {
            log.error("fail to execute query", e);
            throw e;
        }
    }

    @Override
    public void remove(UUID customerId) throws DataAccessException {
        jdbcTemplate.update(DELETE_BY_ID_SQL, Collections.singletonMap("customerId", customerId.toString()));
    }

    public void clear() {
        jdbcTemplate.update(DELETE_SQL, Collections.emptyMap());
    }

    private final RowMapper<Customer> customerRowMapper = (rs, rowNum) -> {
        UUID customerId = UUID.fromString((rs.getString("customer_id")));
        String name = rs.getString("name");
        String email = rs.getString("email");
        LocalDateTime lastLoginAt = rs.getTimestamp("last_login_at") != null ? rs.getTimestamp("last_login_at").toLocalDateTime() : null;
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        return Customer.of(customerId, name, email, lastLoginAt, createdAt);
    };

    private Map<String, Object> customerToMap(Customer customer) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerId", customer.getCustomerId().toString());
        paramMap.put("name", customer.getName());
        paramMap.put("email", customer.getEmail());
        paramMap.put("lastLoginAt", customer.getLastLoginAt());
        paramMap.put("createdAt", customer.getCreatedAt());
        return paramMap;
    }

}
