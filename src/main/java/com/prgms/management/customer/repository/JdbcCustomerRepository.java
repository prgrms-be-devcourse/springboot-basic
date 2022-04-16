package com.prgms.management.customer.repository;

import com.prgms.management.customer.model.Customer;
import com.prgms.management.customer.model.CustomerType;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer save(Customer customer) {
        int result = jdbcTemplate.update(
                "INSERT INTO customer (id, name, type, email, last_login_at, created_at) " +
                        "VALUES (UNHEX(REPLACE(:id, '-', '')), :name, :type, :email, :lastLoginAt, :createdAt)",
                getCustomerMap(customer));

        if (result == 1) {
            return customer;
        }

        throw new RuntimeException("고객 정보 저장에 실패하였습니다.");
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }

    @Override
    public Customer findById(UUID id) {
        return null;
    }

    @Override
    public Customer findByEmail(String email) {
        return null;
    }

    @Override
    public List<Customer> findByType(CustomerType type) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public void removeById(UUID id) {

    }

    @Override
    public void removeAll() {

    }

    private UUID toUUID(byte[] bytes) {
        var buffer = ByteBuffer.wrap(bytes);
        return new UUID(buffer.getLong(), buffer.getLong());
    }

    private Customer mapToCustomer(ResultSet set) throws SQLException {
        UUID id = toUUID(set.getBytes("id"));
        String name = set.getString("name");
        CustomerType type = CustomerType.of(set.getString("type"));
        String email = set.getString("email");
        Timestamp lastLoginAt = set.getTimestamp("last_login_at");
        Timestamp createdAt = set.getTimestamp("created_at");
        return new Customer(id, name, type, email, lastLoginAt, createdAt);
    }

    private Map<String, Object> getCustomerMap(Customer customer) {
        return new HashMap<>() {{
            put("id", customer.getId().toString());
            put("name", customer.getName());
            put("type", customer.getType().toString());
            put("email", customer.getEmail());
            put("lastLoginAt", customer.getLastLoginAt());
            put("createdAt", customer.getCreatedAt());
        }};
    }
}
