package com.programmers.application.repository.customer;

import com.programmers.application.domain.customer.Customer;
import com.programmers.application.repository.sql.builder.DeleteSqlBuilder;
import com.programmers.application.repository.sql.builder.InsertSqlBuilder;
import com.programmers.application.repository.sql.builder.SelectSqlBuilder;
import com.programmers.application.repository.sql.builder.UpdateSqlBuilder;
import com.programmers.application.util.UUIDMapper;
import org.springframework.dao.EmptyResultDataAccessException;
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
        String sql = new InsertSqlBuilder().insertInto("customer")
                .columns("customer_id, name, email, created_at, last_login_at")
                .values(":customerId, :name, :email, :createdAt, :lastLoginAt")
                .build();
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("customerId", UUIDMapper.toBytes(customer.getCustomerId())).addValue("name", customer.getName()).addValue("email", customer.getEmail()).addValue("createdAt", customer.getCreatedAt()).addValue("lastLoginAt", customer.getLastLoginAt());
        namedParameterJdbcTemplate.update(sql, params);
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        String sql = new SelectSqlBuilder()
                .select("*")
                .from("customer")
                .build();
        return namedParameterJdbcTemplate.query(sql, getCustomerRowMapper());
    }

    @Override
    public Optional<Customer> findByCustomerId(UUID customerId) {
        String sql = new SelectSqlBuilder()
                .select("*")
                .from("customer")
                .where("customer_id = :customerId")
                .build();
        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("customerId", UUIDMapper.toBytes(customerId));
        try {
            Customer customer = namedParameterJdbcTemplate.queryForObject(sql, paramMap, getCustomerRowMapper());
            return Optional.of(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        String sql = new SelectSqlBuilder()
                .select("*")
                .from("customer")
                .where("email = :email")
                .build();
        HashMap<String, String> param = new HashMap<>();
        param.put("email", email);
        Customer customer = namedParameterJdbcTemplate.queryForObject(sql, param, getCustomerRowMapper());
        return Optional.ofNullable(customer);
    }

    @Override
    public Customer update(Customer customer) {
        String sql = new UpdateSqlBuilder()
                .update("customer")
                .set("name = :name, last_login_at = :lastLoginAt")
                .where("customer_id = :customerId")
                .build();
        SqlParameterSource paramMap = new MapSqlParameterSource().addValue("customerId", UUIDMapper.toBytes(customer.getCustomerId())).addValue("name", customer.getName()).addValue("lastLoginAt", customer.getLastLoginAt());
        namedParameterJdbcTemplate.update(sql, paramMap);
        return customer;
    }

    @Override
    public void deleteByCustomerId(UUID customerId) {
        String sql = new DeleteSqlBuilder()
                .deleteFrom("customer")
                .where("customer_id = :customerId")
                .build();
        SqlParameterSource param = new MapSqlParameterSource().addValue("customerId", UUIDMapper.toBytes(customerId));
        namedParameterJdbcTemplate.update(sql, param);
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
