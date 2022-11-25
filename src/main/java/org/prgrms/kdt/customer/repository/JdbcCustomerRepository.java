package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.exception.NotPresentInRepositoryException;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.util.ConvertUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> mapToCustomer(resultSet);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static Customer mapToCustomer(ResultSet resultSet) throws SQLException {
        UUID customerId = ConvertUtil.toUUID(resultSet.getBytes("customer_id"));
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null
                ? resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        boolean isBlacklist = resultSet.getBoolean("is_blacklist");

        return new Customer(customerId, name, email, lastLoginAt, createdAt, isBlacklist);
    }

    @Override
    public Customer insert(Customer customer) {
        Map<String, Object> paramMap = toParamMap(customer);
        int insertCount = jdbcTemplate.update("INSERT INTO customers(customer_id, name, email, created_at, is_blacklist) VALUES (UUID_TO_BIN(:customerId), :name, :email, :createdAt, :isBlacklist)",
                paramMap);

        if (insertCount != 1) {
            throw new IllegalArgumentException("아무것도 삽입되지 않았습니다.");
        }

        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        Map<String, Object> paramMap = toParamMap(customer);
        int updateCount = jdbcTemplate.update(
                "UPDATE customers SET name = :name, email = :email, created_at = :createdAt, last_login_at = :lastLoginAt, is_blacklist = :isBlacklist WHERE customer_id = UUID_TO_BIN(:customerId)",
                paramMap);

        if (updateCount != 1) {
            throw new IllegalArgumentException("아무것도 갱신되지 않았습니다.");
        }

        return customer;
    }

    @Override
    public List<Customer> getAllStoredCustomer() {
        return jdbcTemplate.query("SELECT * FROM customers", customerRowMapper);
    }

    @Override
    public void clear() {
        jdbcTemplate.update("DELETE FROM customers", Collections.emptyMap());
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            throw new NotPresentInRepositoryException("해당 customer가 존재하지 않습니다.", e);
        }
    }

    private Map<String, Object> toParamMap(Customer customer) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("customerId", customer.getCustomerId().toString().getBytes());
        hashMap.put("name", customer.getName());
        hashMap.put("email", customer.getEmail());
        hashMap.put("createdAt", customer.getCreatedAt());
        hashMap.put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        hashMap.put("isBlacklist", customer.isBlacklist());

        return hashMap;
    }
}
