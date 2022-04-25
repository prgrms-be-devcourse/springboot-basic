package com.prgrms.voucher_manager.customer.repository;

import com.prgrms.voucher_manager.customer.Customer;
import com.prgrms.voucher_manager.customer.SimpleCustomer;
import com.prgrms.voucher_manager.infra.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile({"default","test"})
public class JdbcCustomerRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private static final String INSERT_SQL = "INSERT INTO customers(customer_id, name, email, created_at) VALUES (UUID_TO_BIN(:customerId), :name, :email, :createdAt)";
    private static final String UPDATE_BY_ID_SQL = "UPDATE customers SET name = :name, email = :email, last_login_at = :lastLoginAt WHERE customer_id = UUID_TO_BIN(:customerId)";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM customers";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)";
    private static final String SELECT_COUNT_ALL_SQL = "SELECT COUNT(*) FROM customers";

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customerId = Utils.toUUID(resultSet.getBytes("customer_id"));
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        LocalDateTime lastLoginAt =
                resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new SimpleCustomer(customerId, customerName, email, lastLoginAt, createdAt);
    };


    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer insert(Customer customer) {

        int update = jdbcTemplate.update(INSERT_SQL, toParamMap(customer));
        if(update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public Integer count() {
        return jdbcTemplate.queryForObject(SELECT_COUNT_ALL_SQL,Collections.emptyMap(),Integer.class);
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, Collections.singletonMap("customerId", customerId.toString().getBytes()), customerRowMapper));
        } catch (EmptyResultDataAccessException exception) {
            logger.info("Customer findById - 해당 데이터가 없습니다.");
            return Optional.empty();
        }
    }

    @Override
    public Customer update(Customer customer) {
        int update = jdbcTemplate.update(UPDATE_BY_ID_SQL, toParamMap(customer));
        if(update != 1) {
            throw new RuntimeException("Customer update - 업데이트 되지 않았습니다.");
        }
        return customer;
    }

    @Override
    public Customer delete(Customer customer) {
        jdbcTemplate.update(DELETE_BY_ID_SQL, toParamMap(customer));
        return customer;
    }

    private Map<String, Object> toParamMap(Customer customer) {
        HashMap<String, Object> hashMap = new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        }};
        return hashMap;
    }

}
