package org.devcourse.springbasic.domain.customer.dao;

import lombok.RequiredArgsConstructor;
import org.devcourse.springbasic.domain.customer.domain.Customer;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CustomerJdbcRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public UUID save(Customer customer) {
        String QUERY = "insert into customers(customer_id, name, email, created_at) " +
                     "values (UUID_TO_BIN(:customerId), :name, :email, :createdAt)";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("customerId", customer.getCustomerId().toString().getBytes())
                .addValue("name", customer.getName())
                .addValue("email", customer.getEmail())
                .addValue("createdAt", customer.getCreatedAt());

        try {
            jdbcTemplate.update(QUERY, paramMap);
            return customer.getCustomerId();
        } catch (DuplicateKeyException duplicateKeyException) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }

    @Override
    public UUID update(Customer customer) {
        String QUERY = "update customers set name = :name, email = :email " +
                     "where customer_id = UUID_TO_BIN(:customerId)";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("customerId", customer.getCustomerId().toString().getBytes())
                .addValue("name", customer.getName())
                .addValue("email", customer.getEmail());

        jdbcTemplate.update(QUERY, paramMap);
        return customer.getCustomerId();
    }

    @Override
    public void lastLoginUpdate(Customer customer) {
        String QUERY = "update customers set last_login_at = :lastLoginAt " +
                     "where customer_id = UUID_TO_BIN(:customer_id)";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("lastLoginAt", customer.getLastLoginAt());
        jdbcTemplate.update(QUERY, paramMap);
    }

    @Override
    public List<Customer> findAll() {
        String QUERY = "select * from customers";
        return jdbcTemplate.query(QUERY, CUSTOMER_ROW_MAPPER);

    }

    @Override
    public Customer findById(UUID customerId) {

        String QUERY = "select * from customers where customer_id = UUID_TO_BIN(:customerId)";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("customerId", customerId.toString().getBytes());
        try {
            return jdbcTemplate.queryForObject(QUERY, paramMap, CUSTOMER_ROW_MAPPER);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            throw new IllegalArgumentException("해당 ID를 가진 회원이 없습니다.");
        }
    }

    @Override
    public Customer findByName(String name) {
        String QUERY = "select * from customers where name = :name";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("name", name);
        try {
            return jdbcTemplate.queryForObject(QUERY, paramMap, CUSTOMER_ROW_MAPPER);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            throw new IllegalArgumentException("해당 이름을 가진 회원이 없습니다.");
        }
    }


    @Override
    public Customer findByEmail(String email) {
        String QUERY = "select * from customers where email = :email";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("email", email);
        try {
            return jdbcTemplate.queryForObject(QUERY, paramMap, CUSTOMER_ROW_MAPPER);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            throw new IllegalArgumentException("해당 이메일을 가진 회원이 없습니다.");
        }
    }


    private static final RowMapper<Customer> CUSTOMER_ROW_MAPPER = (resultSet, i) -> {
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;

        return new Customer(customerId, name, email, lastLoginAt, createdAt);
    };

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
