package com.voucher.vouchermanagement.repository.customer;

import com.voucher.vouchermanagement.model.customer.Customer;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.voucher.vouchermanagement.repository.JdbcUtils.toLocalDateTime;
import static com.voucher.vouchermanagement.repository.JdbcUtils.toUUID;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(Customer customer) {
        int update = jdbcTemplate.update("INSERT INTO customers VALUES(UNHEX(REPLACE(:id, '-', '')), :name, :email, :lastLoginAt, :createdAt)",
                toParamMap(customer));

        if (update != 1) {
            throw new RuntimeException("Nothing was inserted.");
        }
    }


    @Override
    public Optional<Customer> findById(UUID id) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject("SELECT * FROM customers WHERE id = UNHEX(REPLACE(:id, '-',''))",
                        Collections.singletonMap("id", id.toString().getBytes()),
                        customerRowMapper)
        );
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM customers",
                customerRowMapper
        );
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM customers", Collections.emptyMap());
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID id = toUUID(resultSet.getBytes("id"));
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        LocalDateTime lastLoginAt = toLocalDateTime(resultSet.getTimestamp("last_login_at"));
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));

        return new Customer(
                id,
                name,
                email,
                lastLoginAt,
                createdAt
        );
    };

    private Map<String, Object> toParamMap(Customer customer) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", customer.getId().toString().getBytes());
        paramMap.put("name", customer.getName());
        paramMap.put("email", customer.getEmail());
        paramMap.put("lastLoginAt", customer.getLastLoginAt());
        paramMap.put("createdAt", customer.getCreatedAt());
        return paramMap;
    }
}
