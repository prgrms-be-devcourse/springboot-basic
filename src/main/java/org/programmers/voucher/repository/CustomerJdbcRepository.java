package org.programmers.voucher.repository;

import org.programmers.voucher.domain.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static org.programmers.voucher.repository.JdbcUtils.toUUID;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer insert(Customer customer) {
        var update = jdbcTemplate.update("insert into customers(customer_id, name, email, created_at " +
                "values(UUID_TO_BIN(:customerId), :name, :email, :createdAt)",
                toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        var update = jdbcTemplate.update("update customers set name = :name, email = :email, last_login_at = :lastLoginAt " +
                "where customer_id = UUID_TO_BIN(:customerId)",
                toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers " +
                        "where customer_id = UUID_TO_BIN(:customerId)",
                Collections.singletonMap("customerId", customerId.toString().getBytes()),
                customerRowMapper));
    }

    @Override
    public Optional<Customer> findByName(String name) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers " +
                        "where name = :name",
                Collections.singletonMap("name", name),
                customerRowMapper));
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers " +
                        "where email = :email",
                Collections.singletonMap("email", email),
                customerRowMapper));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from customers", Collections.emptyMap());
    }


    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at").toLocalDateTime();
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
    };

    private Map<String, Object> toParamMap(Customer customer) {
        Map<String, Object> map = new HashMap<>();
        map.put("customerId", customer.getCustomerId().toString().getBytes());
        map.put("name", customer.getName());
        map.put("email", customer.getEmail());
        map.put("lastLoginAt", Timestamp.valueOf(customer.getLastLoginAt()));
        map.put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
        return map;
    }
}
