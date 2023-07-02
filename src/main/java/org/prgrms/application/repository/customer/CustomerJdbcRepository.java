package org.prgrms.application.repository.customer;

import org.prgrms.application.domain.customer.Customer;
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
public class CustomerJdbcRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);
    private static final int HAS_UPDATE = 1;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //데이터베이스에서 데이터를 가져오는 역할(컬럼별로 데이터를 가져온다.)
    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        Long customerId = resultSet.getLong("customer_id");
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Customer(customerId, customerName, email, lastLoginAt, createdAt);};

    // 맵의 키를 파라미터로 변경
    private Map<String, Object> toParamMap(Customer customer) {return new HashMap<>() {{
            put("customerId", customer.getCustomerId());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("cratedAt", Timestamp.valueOf(customer.getCreatedAt()));
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        }};}

    @Override
    public Customer insert(Customer customer) {
        int update = jdbcTemplate.update("INSERT INTO customers(customer_id, name, email, created_at) VALUES (:customerId, :name, :email, :cratedAt)",
                toParamMap(customer));
        if (update != HAS_UPDATE) {
            throw new RuntimeException("Noting was inserted");
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        var update = jdbcTemplate.update("UPDATE customers SET name = :name, email = :email, last_login_at = :lastLoginAt WHERE customer_id = :customerId",
                toParamMap(customer)
        );
        if (update != HAS_UPDATE) {
            throw new RuntimeException("Noting was updated");
        }
        return customer;
    }

    //empty map을 사용한 이유 : queryForObject 가 특정한 객체를 요구하는 메서드 특성 때문에  map이나 sql파라미터소스를 전달해야 한다. 대신 가짜 맵을 전달
    @Override
    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from customers", Collections.emptyMap(), Integer.class);
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", customerRowMapper);
    }


    //getbytes 메소드 사용 이유 : 데이터를 전송하거나 저장을 위해선 바이트 형태로 변환해야 한다.
    //Collections.singletonMap : 메소드의 주요 목적은 단일 항목을 가지는 작은 맵을 생성하는 것
    @Override
    public Optional<Customer> findById(Long customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers WHERE customer_id = :customerId",
                    Collections.singletonMap("customerId", customerId),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }


    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers WHERE name = :name",
                    Collections.singletonMap("name", name),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers WHERE email = :email",
                    Collections.singletonMap("email", email),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.getJdbcTemplate().update("DELETE FROM customers", Collections.emptyMap());
    }
}
