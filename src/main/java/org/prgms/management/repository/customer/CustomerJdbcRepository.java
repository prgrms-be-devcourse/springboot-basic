package org.prgms.management.repository.customer;

import org.prgms.management.model.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

import static org.prgms.management.util.JdbcUtils.toUUID;

@Repository
@Profile({"local-db", "dev", "test"})
public class CustomerJdbcRepository implements CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer insert(Customer customer) {
        var executeUpdate = jdbcTemplate.update("INSERT INTO customers(customer_id, name, created_at) " +
                "VALUES (UUID_TO_BIN(:customerId), :name, :createdAt)", toParamMap(customer));

        if (executeUpdate != 1) {
            return null;
        }

        return customer;
    }

    @Override
    public List<Customer> findAll() {
        try {
            return jdbcTemplate.query("SELECT * FROM customers", rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return List.of();
        }
    }

    @Override
    public Customer findById(UUID customerId) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Customer findByName(String name) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM customers WHERE name = :name",
                    Collections.singletonMap("name", name),
                    rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Customer update(Customer customer) {
        var executeUpdate = jdbcTemplate.update(
                "UPDATE customers SET name = :name " +
                        "WHERE customer_id = UUID_TO_BIN(:customerId)",
                toParamMap(customer));

        if (executeUpdate != 1) {
            return null;
        }

        return customer;
    }

    @Override
    public Customer delete(Customer customer) {
        var executeUpdate = jdbcTemplate.update(
                "DELETE FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                toParamMap(customer));

        if (executeUpdate != 1) {
            return null;
        }

        return customer;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM customers", Collections.emptyMap());
    }

    private Map<String, Object> toParamMap(Customer customer) {
        Map<String, Object> map = new HashMap<>();
        map.put("customerId", customer.getCustomerId().toString().getBytes());
        map.put("name", customer.getName());
        map.put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
        return map;
    }

    private static final RowMapper<Customer> rowMapper = (resultSet, i) -> {
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var name = resultSet.getString("name");
        var isBlacklist = resultSet.getBoolean("is_blacklist");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return Customer.getCustomer(customerId, name, isBlacklist, createdAt);
    };
}
