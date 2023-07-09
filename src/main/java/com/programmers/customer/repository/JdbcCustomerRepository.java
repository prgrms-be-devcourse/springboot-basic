package com.programmers.customer.repository;

import com.programmers.customer.domain.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private static final String NOT_FOUND_ERROR_MESSAGE = "[ERROR] 해당 요청에 대한 결과를 찾을 수 없습니다.";
    private static final String UPDATE_FAIL_MESSAGE = "[ERROR] 수정 요청에 대한 값을 찾지 못했습니다..";

    private NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Customer save(Customer customer) {
        String sql = "insert into customer (id, name, created_at, modified_at)" +
                " values(:id, :name, :createdAt, :modifiedAt)";
        jdbcTemplate.update(sql, converParameterToMap(customer));
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        String sql = "update customer set name = :name , modified_at = :modifiedAt where id = :id";
        int update = jdbcTemplate.update(sql, converParameterToMap(customer));
        if (update != 1) {
            throw new RuntimeException(UPDATE_FAIL_MESSAGE);
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        String sql = "select * from customer";
        return jdbcTemplate.query(sql, customerRowMapper());
    }

    @Override
    public Customer findById(UUID customerId) {
        String sql = "select * from customer where id = :id";
        try {
            return jdbcTemplate.queryForObject(sql,
                    Collections.singletonMap("id", customerId.toString()),
                    customerRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException(NOT_FOUND_ERROR_MESSAGE);
        }
    }

    @Override
    public void deleteById(UUID customerId) {
        String sql = "delete from customer where id = :id";
        jdbcTemplate.update(sql, Collections.singletonMap("id", customerId.toString()));
    }

    private Map<String, Object> converParameterToMap (Customer customer) {
        return new HashMap<>() {{
            put("id", customer.getCustomerId().toString());
            put("name", customer.getName());
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
            Timestamp modifiedAt = null;
            if (customer.getModifiedAt() != null)
                modifiedAt = Timestamp.valueOf(customer.getModifiedAt());
            put("modifiedAt", modifiedAt);
        }};
    }

    private RowMapper<Customer> customerRowMapper() {
        return (rs, rowNum) -> {
            UUID customerId = UUID.fromString(rs.getString("id"));
            String name = rs.getString("name");
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            LocalDateTime modifiedAt = null;
            if (rs.getTimestamp("modified_at") != null)
                modifiedAt = rs.getTimestamp("modified_at").toLocalDateTime();
            return new Customer(customerId, name, createdAt, modifiedAt);
        };
    }
}
