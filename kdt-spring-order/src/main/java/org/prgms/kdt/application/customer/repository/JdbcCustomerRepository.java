package org.prgms.kdt.application.customer.repository;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.prgms.kdt.application.customer.domain.Customer;
import org.prgms.kdt.application.customer.exception.CustomerDuplicateKeyException;
import org.prgms.kdt.application.util.UuidUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        var customerId = UuidUtils.toUUID(resultSet.getBytes("customer_id"));
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();
        return new Customer(customerId, customerName, email, createdAt, updatedAt);
    };

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes(StandardCharsets.UTF_8));
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
            put("updatedAt", Timestamp.valueOf(customer.getUpdatedAt()));
        }};
    }

    @Override
    public Customer insert(Customer customer) {
        try {
            String sql = "INSERT INTO customers(customer_id, name, email, created_at, updated_at) VALUES (UNHEX(REPLACE(:customerId, '-', '')), :name, :email, :createdAt, :updatedAt)";
            int updateRow = jdbcTemplate.update(sql, toParamMap(customer));
            if (updateRow != 1) {
                throw new IllegalStateException("Customer가 정상적으로 생성 되지 않았습니다.");
            }
        } catch (DuplicateKeyException e) {
            log.error("Customer 중복된 Key 발생");
            throw new CustomerDuplicateKeyException("Customer 중복된 Key 발생",e);
        }

        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        try {
            String sql = "UPDATE customers SET name = :name, email = :email WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))";
            int updateRow = jdbcTemplate.update(sql, toParamMap(customer));
            if (updateRow != 1) {
                throw new IllegalStateException("Customer가 정상적으로 업데이트 되지 않았습니다.");
            }
        } catch (DuplicateKeyException e) {
            log.error("Customer 중복된 Key 발생");
            throw new CustomerDuplicateKeyException("Customer 중복된 Key 발생",e);
        }

        return customer;
    }

    @Override
    public List<Customer> findAll() {
        String sql = "select * from customers";
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        String sql = "select * from customers where customer_id = UNHEX(REPLACE(:customerId, '-', ''))";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                Collections.singletonMap("customerId", customerId.toString().getBytes()),
                customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.error("Customer가 조회되지 않았습니다.", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        String sql = "select * from customers where name = :name";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                Collections.singletonMap("name", name),
                customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result", e);
            return Optional.empty();
        }
    }


    @Override
    public int delete(UUID customerId) {
        String sql = "DELETE FROM customers WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))";
        return jdbcTemplate.update(sql, Collections.singletonMap("customerId", customerId.toString().getBytes()));
    }

    @Override
    public int deleteAll() {
        String sql = "DELETE FROM customers";
        return jdbcTemplate.update(sql, Collections.emptyMap());
    }
}
