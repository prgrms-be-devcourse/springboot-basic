package com.programmers.application.repository.customer;

import com.programmers.application.domain.customer.Customer;
import com.programmers.application.util.UUIDMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

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
        String sql = "INSERT INTO customer (customer_id, email, name, created_at, last_login_at) VALUES (:customerId, :name, :email, :createdAt, :lastLoginAt)";
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
        return null;
    }

    @Override
    public Optional<Customer> findByCustomerId(UUID customerId) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }

    @Override
    public void deleteByCustomerId(UUID customerId) {

    }
}
