package com.prgms.management.customer.repository;

import com.prgms.management.common.exception.DeleteFailException;
import com.prgms.management.common.exception.FindFailException;
import com.prgms.management.common.exception.SaveFailException;
import com.prgms.management.common.exception.UpdateFailException;
import com.prgms.management.customer.model.Customer;
import com.prgms.management.customer.model.CustomerType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Repository
@Profile({"default"})
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

        throw new SaveFailException("고객 정보 저장에 실패하였습니다.");
    }

    @Override
    public Customer update(Customer customer) {
        int result = jdbcTemplate.update("UPDATE customer SET name = :name, type = :type WHERE id = UNHEX(REPLACE" +
                "(:id, '-', ''))",
            getCustomerMap(customer));

        if (result == 1) {
            return customer;
        }

        throw new UpdateFailException("고객 정보 수정에 실패하였습니다.");
    }

    @Override
    public Customer findById(UUID id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from customer WHERE id = UNHEX(REPLACE(:id, '-', ''))",
                Collections.singletonMap("id", id.toString()),
                (rs, rowNum) -> mapToCustomer(rs));
        } catch (EmptyResultDataAccessException e) {
            throw new FindFailException();
        }
    }

    @Override
    public Customer findByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from customer WHERE email = :email",
                Collections.singletonMap("email", email),
                (rs, rowNum) -> mapToCustomer(rs));
        } catch (EmptyResultDataAccessException e) {
            throw new FindFailException();
        }
    }

    @Override
    public List<Customer> findByType(CustomerType type) {
        return jdbcTemplate.query("SELECT * from customer WHERE type = :type ORDER BY created_at DESC",
            Collections.singletonMap("type", type.toString()),
            (rs, rowNum) -> mapToCustomer(rs));
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * from customer ORDER BY created_at DESC",
            (rs, rowNum) -> mapToCustomer(rs));
    }

    @Override
    public void removeById(UUID id) {
        int result = jdbcTemplate.update("DELETE FROM customer WHERE id = UNHEX(REPLACE(:id, '-', ''))",
            Collections.singletonMap("id", id.toString()));
        if (result != 1) {
            throw new DeleteFailException("고객 정보 삭제에 실패하였습니다.");
        }
    }

    @Override
    public void removeAll() {
        jdbcTemplate.update("DELETE FROM customer WHERE id is not null", Collections.emptyMap());
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
