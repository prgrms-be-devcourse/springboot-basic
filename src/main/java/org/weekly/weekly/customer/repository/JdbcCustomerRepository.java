package org.weekly.weekly.customer.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.weekly.weekly.customer.domain.Customer;
import org.weekly.weekly.customer.exception.CustomerException;
import org.weekly.weekly.global.handler.ExceptionCode;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile("!dev")
@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static UUID toUUID(byte[] bytes) {
        var buffer = ByteBuffer.wrap(bytes);
        return new UUID(buffer.getLong(), buffer.getLong());
    }

    @Override
    public Customer insert(Customer customer) {
        String sql = "INSERT INTO customers(customer_id, name, email, create_at) VALUES(UUID_TO_BIN(?), ?, ?, ?)";
        int insert = jdbcTemplate.update(sql,
                uuidToBytes(customer.getCustomerId()),
                customer.getName(),
                customer.getEmail(),
                Timestamp.valueOf(customer.getCreateAt()));

        if (insert != 1) {
            throw new CustomerException(ExceptionCode.SQL_INSERT_ERROR);
        }
        return customer;
    }

    @Override
    public void deleteByEmail(String email) {
        String sql = "DELETE FROM customers WHERE email = ?";
        jdbcTemplate.update(sql, email);
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM customers";
        jdbcTemplate.update(sql);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        String sql = "SELECT * FROM customers WHERE email = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapToCustomer(rs), email));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM customers";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapToCustomer(rs));
    }

    @Override
    public Customer update(Customer customer, String newEmail) {
        String sql = "UPDATE customers SET email = ? WHERE email = ?";

        String beforeEmail = customer.getEmail();
        customer.updateEmail(newEmail);

        int update = jdbcTemplate.update(sql,
                customer.getEmail(),
                beforeEmail);

        if (update != 1) {
            throw new CustomerException(ExceptionCode.SQL_ERROR);
        }
        return customer;
    }

    private Customer mapToCustomer(ResultSet resultSet) throws SQLException {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        LocalDateTime createAt = resultSet.getTimestamp("create_at") == null ? null : resultSet.getTimestamp("create_at").toLocalDateTime();

        return new Customer(customerId, name, email, createAt);
    }

    private byte[] uuidToBytes(UUID voucherId) {
        return voucherId.toString().getBytes();
    }
}
