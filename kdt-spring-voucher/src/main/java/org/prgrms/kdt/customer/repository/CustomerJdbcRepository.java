package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.customer.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Customer insert(Customer customer) {
        String insertSql = "insert into customers(customer_id,name,email,created_at) values(UUID_TO_BIN(:customerId),:name,:email,:createAt)";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("customerId", customer.getCustomerId().toString().getBytes())
            .addValue("name", customer.getName())
            .addValue("email", customer.getEmail())
            .addValue("createAt", Timestamp.valueOf(customer.getCreateAt()));
        jdbcTemplate.update(insertSql, parameterSource);
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        String updateSql = "update customers set name=:name, email=:email, last_login_at=:lastLoginAt where customer_id=UUID_TO_BIN(:customerId) ";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("name", customer.getName())
            .addValue("email", customer.getEmail())
            .addValue("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null)
            .addValue("customerId", customer.getCustomerId().toString().getBytes());
        jdbcTemplate.update(updateSql, parameterSource);
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        String findAllSql = "select * from customers";
        return jdbcTemplate.query(findAllSql, customerRowMapper());
    }

    private RowMapper<Customer> customerRowMapper() {
        return (rs, rowNum) -> {
            String customerName = rs.getString("name");
            String email = rs.getString("email");
            UUID customerId = toUUID(rs.getBytes("customer_id"));
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            LocalDateTime lastLoginAt = rs.getTimestamp("last_login_at") != null ?
                rs.getTimestamp("last_login_at").toLocalDateTime() : null;
            return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
        };
    }


    @Override
    public Optional<Customer> findById(UUID customerId) {
        String findByIdSql = "select * from customers where customer_id = UUID_TO_BIN(:customerId)";
        Map<String, Object> param = Map.of("customerId", customerId.toString().getBytes());
        try {
            Customer customer = jdbcTemplate.queryForObject(findByIdSql, param, customerRowMapper());
            return Optional.of(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        String findByNameSql = "select * from customers where name=:name";
        Map<String, Object> param = Map.of("name", name);
        try {
            Customer customer = jdbcTemplate.queryForObject(findByNameSql, param, customerRowMapper());
            return Optional.of(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        String findByEmailSql = "select * from customers where email=:email";
        Map<String, Object> param = Map.of("email", email);
        try {
            Customer customer = jdbcTemplate.queryForObject(findByEmailSql, param, customerRowMapper());
            return Optional.of(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        String deleteAllSql = "delete from customers";
        Map<String, Object> param = Map.of();
        jdbcTemplate.update(deleteAllSql, param);
    }

    private UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
