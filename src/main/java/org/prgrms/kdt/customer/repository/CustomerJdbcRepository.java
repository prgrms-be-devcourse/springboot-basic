package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.customer.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);

    private final DataSource datasource;

    public CustomerJdbcRepository(DataSource datasource) {
        this.datasource = datasource;
    }

    @Override
    public Customer insert(Customer customer) {
        try (
                Connection connection = datasource.getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO customers(customer_id, name, email, created_at) VALUES (UUID_TO_BIN(?), ?, ?, ?)");
        ) {
            statement.setBytes(1, customer.getCustomerId().toString().getBytes());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getEmail());
            statement.setTimestamp(4, Timestamp.valueOf(customer.getCreatedAt()));
            final int excuteUpdate = statement.executeUpdate();
            if (excuteUpdate != 1) {
                throw new RuntimeException("Nothing was inserted");
            }
            return customer;
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
            throw new RuntimeException(throwable);
        }
    }

    @Override
    public Customer update(Customer customer) {
        try (
                Connection connection = datasource.getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE customers SET name = ?, email = ?, last_login_at = ? WHERE customer_id = UUID_TO_BIN(?)");
        ) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setTimestamp(3, customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
            statement.setBytes(4, customer.getCustomerId().toString().getBytes());
            final int excuteUpdate = statement.executeUpdate();
            if (excuteUpdate != 1) {
                throw new RuntimeException("Nothing was updated");
            }
            return customer;
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
            throw new RuntimeException(throwable);
        }
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> allCustomers = new ArrayList<>();

        try (
                Connection connection = datasource.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM customers");
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                mapToCustomer(allCustomers, resultSet);
            }
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
            throw new RuntimeException(throwable);
        }
        return allCustomers;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        List<Customer> allCustomers = new ArrayList<>();

        try (
                Connection connection = datasource.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(?)");
        ) {
            statement.setBytes(1, customerId.toString().getBytes());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    mapToCustomer(allCustomers, resultSet);
                }
            }
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
            throw new RuntimeException(throwable);
        }
        return allCustomers.stream().findFirst();
    }

    @Override
    public Optional<Customer> findByName(String name) {
        List<Customer> allCustomers = new ArrayList<>();

        try (
                Connection connection = datasource.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM customers WHERE name = ?");
        ) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    mapToCustomer(allCustomers, resultSet);
                }
            }
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
            throw new RuntimeException(throwable);
        }
        return allCustomers.stream().findFirst();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        List<Customer> allCustomers = new ArrayList<>();

        try (
                Connection connection = datasource.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM customers WHERE email = ?");
        ) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    mapToCustomer(allCustomers, resultSet);
                }
            }
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
            throw new RuntimeException(throwable);
        }
        return allCustomers.stream().findFirst();
    }

    @Override
    public void deleteAll() {
        try (
                Connection connection = datasource.getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM customers");
        ) {
            statement.executeUpdate();
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
            throw new RuntimeException(throwable);
        }
    }

    private void mapToCustomer(List<Customer> allCustomers, ResultSet resultSet) throws SQLException {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String customerName = resultSet.getString("name");
        String customerEmail = resultSet.getString("email");
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        allCustomers.add(new Customer(customerId, customerName, customerEmail, lastLoginAt, createdAt));
    }

    static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
