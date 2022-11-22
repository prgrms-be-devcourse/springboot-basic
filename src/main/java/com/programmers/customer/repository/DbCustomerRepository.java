package com.programmers.customer.repository;

import com.programmers.customer.domain.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class DbCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DbCustomerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private static final RowMapper<Customer> rowMapper = (rs, rowNum) -> {
        UUID customerId = toUUID(rs.getBytes("customer_id"));
        String email = rs.getString("email");
        String password = rs.getString("password");
        String name = rs.getString("name");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        return new Customer(customerId, email, password, name, createdAt);
    };


    @Override
    public Customer insert(Customer customer) {
        Map<String, ?> paramMap = new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("email", customer.getEmail());
            put("password", customer.getPassword());
            put("name", customer.getName());
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
        }};
        int update = namedParameterJdbcTemplate.update("INSERT INTO customer(customer_id, email, password, name, created_at) VALUES(UUID_TO_BIN(:customerId), :email, :password, :name, :createdAt)", paramMap);
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }


    @Override
    public Customer update(Customer customer) {
        Map<String, ?> paramMap = new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
        }};
        int update = namedParameterJdbcTemplate.update("UPDATE customer SET name= :name WHERE customer_id=UUID_TO_BIN(:customerId)", paramMap);
        if (update != 1) throw new RuntimeException("Nothing was updated");
        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject("SELECT * FROM customer WHERE customer_id = UUID_TO_BIN(:customerId)",
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    rowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject("SELECT * FROM customer WHERE email= :email",
                    Collections.singletonMap("email", email),
                    rowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        return namedParameterJdbcTemplate.query("SELECT * FROM customer", rowMapper);
    }


    @Override
    public void deleteAll() {
        namedParameterJdbcTemplate.update("DELETE FROM customer", Collections.emptyMap());
    }
}

