package com.waterfogsw.voucher.customer.repository;

import com.waterfogsw.voucher.customer.model.Customer;
import com.waterfogsw.voucher.voucher.exception.ResourceNotFoundException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        final var id = resultSet.getLong("id");
        final var name = resultSet.getString("name");
        final var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        final var updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();
        return new Customer(id, name, createdAt, updatedAt);
    };
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("id", customer.getId());
            put("name", customer.getName());
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
            put("updatedAt", Timestamp.valueOf(customer.getCreatedAt()));
        }};
    }

    @Override
    public Customer insert(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException();
        }

        if (customer.getId() == 0) {
            final var insertSQL = "INSERT INTO customers(name, created_at, updated_at) " +
                    "VALUES (:name, :createdAt, :updatedAt)";
            final var affectedRow = jdbcTemplate.update(insertSQL, toParamMap(customer));

            if (affectedRow != 1) {
                throw new IllegalStateException("Nothing was inserted");
            }

            final var id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Collections.emptyMap(), Long.class);

            return Customer.toEntity(id, customer);
        }

        final var updatedNum = jdbcTemplate.update(
                "UPDATE customers SET name = :name, updated_at = :updatedAt WHERE id = :id",
                toParamMap(customer)
        );

        if (updatedNum != 1) {
            throw new IllegalStateException("Error occur while update");
        }

        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", customerRowMapper);
    }

    @Override
    public Customer findById(long id) {
        return jdbcTemplate.query("select * from customers where id = :id",
                        Collections.singletonMap("id", id), customerRowMapper)
                .stream()
                .findFirst()
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteById(long id) {
        final var deleteSQL = "DELETE FROM customers WHERE id = :id";
        final var affectedRow = jdbcTemplate.update(deleteSQL, Collections.singletonMap("id", String.valueOf(id)));

        if (affectedRow != 1) {
            throw new ResourceNotFoundException();
        }
    }
}
