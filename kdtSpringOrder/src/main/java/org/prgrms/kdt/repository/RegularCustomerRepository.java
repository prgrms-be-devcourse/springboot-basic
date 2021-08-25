package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.customer.RegularCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
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
@Primary
public class RegularCustomerRepository implements CustomerRepository{

    private static final Logger logger = LoggerFactory.getLogger(RegularCustomerRepository.class);
    private final DataSource dataSource;

    public RegularCustomerRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Customer insert(Customer customer) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO customers(customer_id, name, email, created_at) VALUES (UUID_TO_BIN(?), ?, ?, ?)")) {
            statement.setBytes(1, customer.getCustomerId().toString().getBytes());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getEmail());
            statement.setTimestamp(4, Timestamp.valueOf(customer.getCreatedAt()));
            int execute = statement.executeUpdate();
            if(execute != 1) {
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
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE customers SET name = ?, email = ?, last_login_at = ? WHERE customer_id = UUID_TO_BIN(?)");) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setTimestamp(3, customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
            statement.setBytes(4, customer.getCustomerId().toString().getBytes());
            int executeUpdate = statement.executeUpdate();
            if(executeUpdate != 1) {
                throw new RuntimeException("Nothing was inserted");
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
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM customers");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                mapToCustomer(allCustomers, resultSet);
            }
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
            throw new RuntimeException(throwable);
        }
        return allCustomers;
    }

    private void mapToCustomer(List<Customer> allCustomers, ResultSet resultSet) throws SQLException {
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        UUID customerId = toUUID(resultSet);
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        allCustomers.add(new RegularCustomer(customerId, customerName, email, lastLoginAt, createdAt));
    }

    static private UUID toUUID(ResultSet resultSet) throws SQLException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(resultSet.getBytes("customer_id"));
        UUID customerId = new UUID(byteBuffer.getLong(), byteBuffer.getLong());
        return customerId;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        List<Customer> allCustomers = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(?)")) {
            statement.setBytes(1, customerId.toString().getBytes());
            try(ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
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
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM customers WHERE name = ?")) {
            statement.setString(1, name);
            try(ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
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
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM customers WHERE email = ?")) {
            statement.setString(1, email);
            try(ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
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
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM customers");) {
            statement.executeUpdate();
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
            throw new RuntimeException(throwable);
        }
    }
}
