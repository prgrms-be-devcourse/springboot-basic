package com.programmers.springbootbasic.domain.customer;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {
    private static final String DATABASE_CUSTOMER_ID = "customer_id";
    private static final String CUSTOMER_ID = "customerId";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Customer> rowMapper = (rs, rowNum) -> {
        UUID customerId = UUID.fromString(rs.getString(DATABASE_CUSTOMER_ID));
        String email = rs.getString(EMAIL);
        String name = rs.getString(NAME);
        return new Customer(customerId, email, name);
    };

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Customer customer) {
        return Map.of(
                CUSTOMER_ID, customer.getCustomerId().toString(),
                NAME, customer.getName(),
                EMAIL, customer.getEmail()
        );
    }

    @Override
    public Optional<Customer> save(Customer customer) {
        int affectedRow = jdbcTemplate.update(
                "INSERT INTO customer(customer_id, name, email) VALUES(:customerId, :name, :email)",
                toParamMap(customer));
        if (affectedRow != 1) {
            return Optional.empty();
        }
        return Optional.of(customer);
    }

    @Override
    public Optional<Customer> update(Customer customer) {
        int affectedRow = jdbcTemplate.update(
                "UPDATE customer SET name = :name, email = :email WHERE customer_id = :cusomterId",
                toParamMap(customer)
        );
        if (affectedRow != 1) {
            return Optional.empty();
        }
        return Optional.of(customer);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            Customer customer = jdbcTemplate.queryForObject(
                    "SELECT * FROM customer WHERE email = :email",
                    Collections.singletonMap(EMAIL, email),
                    rowMapper
            );
            return Optional.ofNullable(customer);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
