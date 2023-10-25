package com.programmers.vouchermanagement.repository.customer;

import com.programmers.vouchermanagement.domain.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
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
        String sql = "select * from customers";
        return jdbcTemplate.query(sql, (resultSet, i) -> mapToCustomer(resultSet));
    }

    @Override
    public List<Customer> findBannedCustomers() {
        String sql = "select * from customers where is_banned = TRUE";
        return jdbcTemplate.query(sql, (resultSet, i) -> mapToCustomer(resultSet));
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        String sql = "select * from customers where id = UUID_TO_BIN(:customerId)";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id.toString());
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    sql,
                    namedParameters,
                    (resultSet, i) -> mapToCustomer(resultSet)));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findByName(String name) {
        String sql = "select * from customers where name like :name";
        SqlParameterSource namedParameters = new MapSqlParameterSource("name", name);
        return jdbcTemplate.query(
                sql,
                namedParameters,
                (resultSet, i) -> mapToCustomer(resultSet));
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        String sql = "INSERT INTO customers VALUES :id, :name, :createdAt, :isBanned";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", customer.getId())
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
        String sql = "UPDATE customers SET :id, :name, :createdAt, :isBanned WHERE id= :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", customer.getId())
                .addValue("name", customer.getName())
                .addValue("createdAt", customer.getCreatedAt())
                .addValue("isBanned", customer.isBanned());
        int affectedRow = jdbcTemplate.update(sql, namedParameters);
        logger.debug("Affected Row on update: {}", affectedRow);
        return customer;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        String sql = "DELETE FROM customers WHERE id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);
        int affectedRow = jdbcTemplate.update(sql, namedParameters);
        logger.debug("Affected Row on delete: {}", affectedRow);
    }

    private Customer mapToCustomer(ResultSet resultSet) throws SQLException {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String customerName = resultSet.getString("name");
        final LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        final boolean isBanned = resultSet.getBoolean("is_banned");
        return new Customer(customerId, customerName, createdAt, isBanned);
    }

    private UUID toUUID(byte[] bytes) {
        final ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
