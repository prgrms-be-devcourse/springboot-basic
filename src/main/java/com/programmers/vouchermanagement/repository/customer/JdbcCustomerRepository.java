package com.programmers.vouchermanagement.repository.customer;

import com.programmers.vouchermanagement.domain.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("dev")
public class JdbcCustomerRepository implements CustomerRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM customer";
        return jdbcTemplate.query(sql, (resultSet, i) -> mapToCustomer(resultSet));
    }

    @Override
    public List<Customer> findBannedCustomers() {
        String sql = "SELECT * FROM customer WHERE is_banned = TRUE";
        return jdbcTemplate.query(sql, (resultSet, i) -> mapToCustomer(resultSet));
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        String sql = "SELECT * FROM customer WHERE id = UUID_TO_BIN(:id)";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id.toString());
        return jdbcTemplate.query(sql, namedParameters, (resultSet, i) -> mapToCustomer(resultSet)).stream().findFirst();
    }

    @Override
    public Optional<Customer> findByName(String name) {
        String sql = "SELECT * FROM customer WHERE name =:name";
        SqlParameterSource namedParameters = new MapSqlParameterSource("name", name);
        return jdbcTemplate.query(sql, namedParameters, (resultSet, i) -> mapToCustomer(resultSet)).stream().findFirst();
    }

    @Override
    public List<Customer> findByNameLike(String name) {
        String sql = "SELECT * FROM customer WHERE name LIKE :hasName";
        SqlParameterSource namedParameters = new MapSqlParameterSource("hasName", "%" + name + "%");
        return jdbcTemplate.query(
                sql,
                namedParameters,
                (resultSet, i) -> mapToCustomer(resultSet));
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        String sql = "INSERT INTO customer (id, name, created_at, is_banned) VALUES (UUID_TO_BIN(:id), :name, :createdAt, :isBanned)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", customer.getId().toString())
                .addValue("name", customer.getName())
                .addValue("createdAt", customer.getCreatedAt())
                .addValue("isBanned", customer.isBanned());
        int affectedRow = jdbcTemplate.update(sql, namedParameters);
        logger.debug("Affected Row on save: {}", affectedRow);
        return customer;
    }

    @Override
    @Transactional
    public Customer update(Customer customer) {
        String sql = "UPDATE customer SET name = :name, created_at = :createdAt, is_banned = :isBanned WHERE id = UUID_TO_BIN(:id)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", customer.getId().toString())
                .addValue("name", customer.getName())
                .addValue("createdAt", customer.getCreatedAt())
                .addValue("isBanned", customer.isBanned());
        int affectedRow = jdbcTemplate.update(sql, namedParameters);
        logger.debug("Affected Row on update: {}", affectedRow);
        return customer;
    }

    @Override
    @Transactional
    public int delete(UUID id) {
        String sql = "DELETE FROM customer WHERE id = UUID_TO_BIN(:id)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id.toString());
        int affectedRow = jdbcTemplate.update(sql, namedParameters);
        logger.debug("Affected Row on delete: {}", affectedRow);
        return affectedRow;
    }

    private Customer mapToCustomer(ResultSet resultSet) throws SQLException {
        final UUID id = toUUID(resultSet.getBytes("id"));
        final String name = resultSet.getString("name");
        final LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        final boolean isBanned = resultSet.getBoolean("is_banned");
        return new Customer(id, name, createdAt, isBanned);
    }

    private UUID toUUID(byte[] bytes) {
        final ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
