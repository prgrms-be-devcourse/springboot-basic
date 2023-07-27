package org.devcourse.springbasic.domain.customer.dao;

import lombok.RequiredArgsConstructor;
import org.devcourse.springbasic.domain.customer.domain.Customer;
import org.devcourse.springbasic.domain.customer.domain.CustomerTable;
import org.devcourse.springbasic.global.exception.custom.DuplicateEmailException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CustomerJdbcRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public UUID save(Customer customer) {
        String QUERY =
                "INSERT INTO " + CustomerTable.TABLE_NAME +
                "(" +
                CustomerTable.CUSTOMER_ID + ", "+
                CustomerTable.NAME + ", " +
                CustomerTable.EMAIL + ", " +
                CustomerTable.CREATED_AT +
                ") " + " VALUES (UUID_TO_BIN(:customerId), :name, :email, :createdAt)";

        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("customerId", customer.getCustomerId().toString().getBytes())
                .addValue("name", customer.getName())
                .addValue("email", customer.getEmail())
                .addValue("createdAt", customer.getCreatedAt());

        try {
            jdbcTemplate.update(QUERY, paramMap);
            return customer.getCustomerId();
        } catch (DuplicateKeyException duplicateKeyException) {
            throw new DuplicateEmailException("이미 존재하는 회원입니다.");
        }
    }

    @Override
    public UUID update(Customer customer) {
        String QUERY =
                "UPDATE " + CustomerTable.TABLE_NAME +
                " SET " +
                CustomerTable.NAME + " = :name, " +
                CustomerTable.EMAIL + " = :email " +
                "WHERE " +
                CustomerTable.CUSTOMER_ID + " = UUID_TO_BIN(:customerId)";

        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("customerId", customer.getCustomerId().toString().getBytes())
                .addValue("name", customer.getName())
                .addValue("email", customer.getEmail());

        UpdateResult updateResult = new UpdateResult(jdbcTemplate.update(QUERY, paramMap));
        if (updateResult.isSucceeded()) {
            return customer.getCustomerId();
        }
        throw new EmptyResultDataAccessException("변경된 회원이 없습니다.", 1);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        String QUERY =  "SELECT * " +
                        " FROM " + CustomerTable.TABLE_NAME +
                        " WHERE " + CustomerTable.CUSTOMER_ID + " = UUID_TO_BIN(:customerId)";

        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("customerId", customerId.toString().getBytes());
        try {
            Customer customer = jdbcTemplate.queryForObject(QUERY, paramMap, CUSTOMER_ROW_MAPPER);
            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findByName(String name) {
        String QUERY =  "SELECT * " +
                        " FROM " + CustomerTable.TABLE_NAME +
                        " WHERE " + CustomerTable.NAME + " = :name";

        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("name", name);
        return jdbcTemplate.query(QUERY, paramMap, CUSTOMER_ROW_MAPPER);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        String QUERY =  "SELECT * " +
                        " FROM " + CustomerTable.TABLE_NAME +
                        " WHERE " + CustomerTable.EMAIL + " = :email";

        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("email", email);
        try {
            Customer customer = jdbcTemplate.queryForObject(QUERY, paramMap, CUSTOMER_ROW_MAPPER);
            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        String QUERY = "SELECT * " +
                     " FROM " + CustomerTable.TABLE_NAME;
        return jdbcTemplate.query(QUERY, CUSTOMER_ROW_MAPPER);
    }

    @Override
    public void deleteById(UUID customerId) {
        String QUERY =
                "DELETE FROM " + CustomerTable.TABLE_NAME +
                " WHERE " + CustomerTable.CUSTOMER_ID + " = UUID_TO_BIN(:customerId)";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("customerId", customerId.toString().getBytes());

        UpdateResult updateResult = new UpdateResult(jdbcTemplate.update(QUERY, paramMap));
        if (!updateResult.isSucceeded()) {
            throw new NoSuchElementException("삭제할 회원이 존재하지 않습니다.");
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
