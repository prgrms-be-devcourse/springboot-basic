package com.voucher.vouchermanagement.repository.customer;

import com.voucher.vouchermanagement.model.customer.Customer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.voucher.vouchermanagement.repository.JdbcUtils.toLocalDateTime;
import static com.voucher.vouchermanagement.repository.JdbcUtils.toUUID;

@Repository("prod")
public class CustomerJdbcRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer insert(Customer customer) throws DataAccessException {
        int update = jdbcTemplate.update("INSERT INTO customers VALUES(UNHEX(REPLACE(:id, '-', '')), :name, :email, :lastLoginAt, :createdAt)",
                toParamMap(customer));

        if (update != 1) {
            throw new RuntimeException("아무것도 삽입되지 않았습니다.");
        }

        return customer;
    }

    @Override
    public Customer update(Customer customer) throws DataAccessException {
        int update = jdbcTemplate.update("UPDATE customers SET name = :name, email = :email, last_login_at = :lastLoginAt, created_at = :createdAt " +
                "WHERE id = UNHEX(REPLACE(:id, '-', ''))", toParamMap(customer));

        if (update != 1) {
            throw new RuntimeException("아무것도 수정되지 않았습니다.");
        }

        return customer;
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
    public Optional<Customer> findByName(String name) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject("SELECT * FROM customers WHERE name = :name",
                        Collections.singletonMap("name", name),
                        customerRowMapper)
        );
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject("SELECT * FROM customers WHERE email = :email",
                        Collections.singletonMap("email", email),
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
