package org.prgms.management.customer.repository;

import org.prgms.management.customer.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;

@Repository
@Profile({"local-db", "dev"})
public class CustomerJdbcRepository implements CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Customer> rowMapper = (resultSet, i) -> {
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var name = resultSet.getString("name");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return Customer.getCustomer(customerId, name, createdAt);
    };

    @Override
    public Optional<Customer> insert(Customer customer) {
        try {
            var executeUpdate = jdbcTemplate.update("INSERT INTO customers(customer_id, name, created_at) " +
                    "VALUES (UUID_TO_BIN(:customerId), :name, :createdAt)", toParamMap(customer));

            if (executeUpdate != 1) {
                return Optional.empty();
            }

            return Optional.of(customer);
        } catch (DuplicateKeyException e) {
            logger.error("Failed insert", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        try {
            return jdbcTemplate.query("SELECT * FROM customers", rowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return List.of();
        }
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    rowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT * FROM customers WHERE name = :name",
                    Collections.singletonMap("name", name),
                    rowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> update(Customer customer) {
        try {
            var executeUpdate = jdbcTemplate.update(
                    "UPDATE customers SET name = :name " +
                            "WHERE customer_id = UUID_TO_BIN(:customerId)",
                    toParamMap(customer));

            if (executeUpdate != 1) {
                return Optional.empty();
            }

            return Optional.of(customer);
        } catch (DuplicateKeyException e) {
            logger.error("Failed update", e);
            return Optional.empty();
        }

    }

    @Override
    public Optional<Customer> delete(Customer customer) {
        var executeUpdate = jdbcTemplate.update(
                "DELETE FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                toParamMap(customer));

        if (executeUpdate != 1) {
            return Optional.empty();
        }

        return Optional.of(customer);
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

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
