package com.programmers.vouchermanagement.repository.customer;

import com.programmers.vouchermanagement.domain.customer.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final DataSource dataSource;

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", (resultSet, i) -> mapToCustomer(resultSet));
    }

    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers where customer_id = UUID_TO_BIN(?)",
                    (resultSet, i) -> mapToCustomer(resultSet),
                    customerId));

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Customer> findByName(String name) {
        return jdbcTemplate.query("select * from customers where name like ? ",
                (resultSet, i) -> mapToCustomer(resultSet),
                "%" + name + "%");
    }

    private Customer mapToCustomer(ResultSet resultSet) throws SQLException {
        UUID customerId = toUUID(resultSet.getBytes("id"));
        String customerName = resultSet.getString("name");
        final LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        final boolean isBanned = resultSet.getBoolean("is_banned");

        return new Customer(customerId, customerName, createdAt, isBanned);
    }

    private UUID toUUID(byte[] bytes) {
        final ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public List<Customer> findAllBannedCustomers() {
        return null;
    }


}
