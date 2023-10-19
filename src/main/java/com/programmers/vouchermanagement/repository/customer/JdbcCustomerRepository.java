package com.programmers.vouchermanagement.repository.customer;

import com.programmers.vouchermanagement.domain.customer.Customer;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final DataSource dataSource;

    public JdbcCustomerRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Customer> findByName(String name) {
        List<Customer> customers = new ArrayList<>();
        try (
                final Connection connection = dataSource.getConnection();
                final PreparedStatement statement = connection.prepareStatement("select * from customers where name like ? ");
        ) {
            statement.setString(1, "%" + name + "%");
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                mapToCustomer(resultSet, customers);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customers;
    }

    private void mapToCustomer(ResultSet resultSet, List<Customer> customers) throws SQLException {
        UUID customerId = toUUID(resultSet.getBytes("id"));
        String customerName = resultSet.getString("name");
        final LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        final boolean isBanned = resultSet.getBoolean("is_banned");
        customers.add(new Customer(customerId, customerName, createdAt, isBanned));
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
