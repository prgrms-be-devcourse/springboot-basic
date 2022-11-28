package com.programmers.customer.repository;

import com.programmers.customer.domain.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    private static final RowMapper<Customer> rowMapper = (rs, rowNum) -> {
        String email = rs.getString("email");
        String name = rs.getString("name");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        return new Customer(email, name, createdAt);
    };


    @Override
    public void insert(Customer customer) {
        Map<String, ?> paramMap = new HashMap<>() {{
            put("email", customer.getEmail());
            put("password", customer.getPassword());
            put("name", customer.getName());
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
        }};
        int update = namedParameterJdbcTemplate.update("INSERT INTO customer(email, password, name, created_at) VALUES(:email, :password, :name, :createdAt)", paramMap);
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
    }


    @Override
    public void update(String email, String name) {
        Map<String, ?> paramMap = new HashMap<>() {{
            put("email", email);
            put("name", name);
        }};
        int update = namedParameterJdbcTemplate.update("UPDATE customer SET name= :name WHERE email=:email", paramMap);
        if (update != 1) throw new RuntimeException("Nothing was updated");
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
    public void deleteByEmail(String email) {
        int update = namedParameterJdbcTemplate.update("DELETE FROM customer WHERE email= :email", Collections.singletonMap("email", email));
        if (update != 1) {
            throw new RuntimeException("Nothing was deleted");
        }
    }

    @Override
    public void deleteAll() {
        namedParameterJdbcTemplate.update("DELETE From customer", Collections.emptyMap());
    }

}

