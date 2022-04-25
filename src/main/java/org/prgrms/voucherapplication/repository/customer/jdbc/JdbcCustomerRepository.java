package org.prgrms.voucherapplication.repository.customer.jdbc;

import org.prgrms.voucherapplication.entity.Customer;
import org.prgrms.voucherapplication.exception.DataNotInsertedException;
import org.prgrms.voucherapplication.exception.DataNotUpdatedException;
import org.prgrms.voucherapplication.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcCustomerRepository implements CustomerRepository{

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String SELECT_ALL_SQL = "SELECT * FROM customers";
    private final String INSERT_SQL = "INSERT INTO customers(customer_id, name, email, created_at) VALUES (UUID_TO_BIN(:customerId), :name, :email, :createdAt)";
    private final String UPDATE_SQL = "UPDATE customers SET name = :name, email = :email, last_login_at = :lastLoginAt WHERE customer_id = UUID_TO_BIN(:customerId)";
    private final String SELECT_BY_ID_SQL = "select * from customers WHERE customer_id = UUID_TO_BIN(:customerId)";
    private final String SELECT_BY_NAME_SQL = "select * from customers WHERE name = :name";
    private final String SELECT_BY_EMAIL_SQL = "select * from customers WHERE email = :email";
    private final String DELETE_ALL_SQL = "DELETE FROM customers";
    private final String DELETE_ISSUED_VOUCHER_SQL = "DELETE FROM vouchers WHERE is_issued = true";

    private static final RowMapper<Customer> customerRowMapper = (resultSet, rowNum) -> {
        String customerName = resultSet.getString("name");// "name"이라는 컬럼의 값을 가져옴
        UUID customerId = Util.toUUID(resultSet.getBytes("customer_id"));    // byte를 UUID 바꿔야 함
        String email = resultSet.getString("email");
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
    };

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>(){{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        }};
    }

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, customerRowMapper);
    }

    @Override
    public Customer insert(Customer customer) {
        int update = jdbcTemplate.update(INSERT_SQL, toParamMap(customer));
        if (update != 1) {
            throw new DataNotInsertedException();
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        try {
            jdbcTemplate.update(UPDATE_SQL, toParamMap(customer));
        } catch (Exception e) {
            throw new DataNotUpdatedException();
        }

        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(SELECT_BY_ID_SQL,
                            Collections.singletonMap("customerId", customerId.toString().getBytes()),
                            customerRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(SELECT_BY_NAME_SQL,
                            Collections.singletonMap("name", name),
                            customerRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(SELECT_BY_EMAIL_SQL,
                            Collections.singletonMap("email", email),
                            customerRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ISSUED_VOUCHER_SQL, Collections.emptyMap());
        jdbcTemplate.update(DELETE_ALL_SQL, Collections.emptyMap());
    }

}
