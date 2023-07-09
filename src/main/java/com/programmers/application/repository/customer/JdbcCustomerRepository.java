package com.programmers.application.repository.customer;

import com.programmers.application.domain.customer.Customer;
import com.programmers.application.util.UUIDMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Customer save(Customer customer) {
        String sql = "INSERT INTO customer (customer_id, name, email, created_at, last_login_at) VALUES (:customerId, :name, :email, :createdAt, :lastLoginAt)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("customerId", UUIDMapper.toBytes(customer.getCustomerId()))
                .addValue("name", customer.getName())
                .addValue("email", customer.getEmail())
                .addValue("createdAt", customer.getCreatedAt())
                .addValue("lastLoginAt", customer.getLastLoginAt());
        namedParameterJdbcTemplate.update(sql, params);
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM customer";
        return namedParameterJdbcTemplate.query(sql, getCustomerRowMapper());
    }

    @Override
    public Optional<Customer> findByCustomerId(UUID customerId) {
        String sql = "SELECT * FROM customer WHERE customer_id = :customerId";
        HashMap<String, Object> param = new HashMap<>();
        param.put("customerId", UUIDMapper.toBytes(customerId));
        Customer customer = namedParameterJdbcTemplate.queryForObject(sql, param, getCustomerRowMapper());
        return Optional.ofNullable(customer);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        String sql = "SELECT * FROM customer WHERE email = :email";
        HashMap<String, String> param = new HashMap<>();
        param.put("email", email);
        Customer customer = namedParameterJdbcTemplate.queryForObject(sql, param, getCustomerRowMapper());
        return Optional.ofNullable(customer);
    }

    @Override
    public Customer update(Customer customer) {
        String sql = "UPDATE customer SET name = :name, last_login_at = :lastLoginAt WHERE customer_id = :customerId";
        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("customerId", UUIDMapper.toBytes(customer.getCustomerId()))
                .addValue("name", customer.getName())
                .addValue("lastLoginAt", customer.getLastLoginAt());
        namedParameterJdbcTemplate.update(sql, paramMap);
        return customer;
    }

    @Override
    public void deleteByCustomerId(UUID customerId) {

    }

    private RowMapper<Customer> getCustomerRowMapper() {
        return (rs, rowNum) -> {
            UUID voucherId = UUIDMapper.toUUID(rs.getBytes("customer_id"));
            String email = rs.getString("email");
            String name = rs.getString("name");
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            LocalDateTime lastLoginAt = rs.getTimestamp("last_login_at").toLocalDateTime();
            return new Customer(voucherId, name, email, createdAt, lastLoginAt);
        };
    }
}
