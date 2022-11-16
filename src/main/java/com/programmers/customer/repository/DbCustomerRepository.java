package com.programmers.customer.repository;

import com.programmers.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

@Profile("dev")
@Repository
public class DbCustomerRepository implements CustomerRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final Logger log = LoggerFactory.getLogger(DbCustomerRepository.class);

    public DbCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final static RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        String customerName = resultSet.getString("name");
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String email = resultSet.getString("email");
        LocalDateTime createAt = resultSet.getTimestamp("create_at").toLocalDateTime();
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;

        return new Customer(customerId, customerName, email, lastLoginAt, createAt);
    };

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("select * from customer where customer_id = UUID_TO_BIN(:customerId)"
                            , singletonMap("customerId", customerId.toString().getBytes()), customerRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customer where name = :name",
                    singletonMap("name", name),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result");
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customer where email = :email",
                    singletonMap("email", email),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result");
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from customer", emptyMap());
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from customer", emptyMap(), Integer.class);
    }

    @Override
    public Customer insert(Customer customer) {
        int count = jdbcTemplate.update("INSERT INTO customer(customer_id, name, email, create_at) values (UUID_TO_BIN(:customerId), :name ,:email, :createAt)", toParamMap(customer));

        if (count != 1) {
            log.error("Got error while closing connection");
            throw new RuntimeException();
        }

        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        int update = jdbcTemplate.update(
                "UPDATE customer SET name = :name, email =:email, last_login_at =:lastLoginAt WHERE customer_id = UUID_TO_BIN(:customerId)",
                toParamMap(customer));

        if (update != 1) {
            log.error("Got error while closing connection");
            throw new RuntimeException();
        }

        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customer", customerRowMapper);
    }


    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("name",customer.getName());
            put("email",customer.getEmail());
            put("createAt", customer.getCreateAt());
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
            put("customerId", customer.getCustomerId().toString().getBytes() );
        }};
    }
}
