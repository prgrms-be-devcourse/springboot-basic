package org.prgrms.kdt;

import org.prgrms.kdt.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class JdbcCustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final String SELECT_BY_NAME_SQL = "SELECT * FROM customers WHERE name = ?";
    private final String SELECT_ALL_SQL = "SELECT * FROM customers";
    private final String INSERT_SQL = "INSERT INTO customers(customer_id, name, email) VALUES (UUID_TO_BIN(?), ?, ?)";
    private final String UPDATE_BY_ID_SQL = "UPDATE customers SET name = ? WHERE customer_id = UUID_TO_BIN(?)";
    private final String DELETE_ALL_SQL = "DELETE FROM customers";

    public List<String> findNames(String name) {
        List<String> names = new ArrayList<>();

        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
                var statement = connection.prepareStatement(SELECT_BY_NAME_SQL);
        ) {
            statement.setString(1, name);
            logger.info("statement -> {}", statement);
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var customerName = resultSet.getString("name");
                    var customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                    var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                    names.add(customerName);
                }
            }
        } catch (SQLException exception) {
            logger.error("Got error while closing connection", exception);
        }
        return names;
    }

    public List<String> findAllName() {
        List<String> names = new ArrayList<>();

        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
                var statement = connection.prepareStatement(SELECT_ALL_SQL);
                var resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                var customerName = resultSet.getString("name");
                var customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                names.add(customerName);
            }

        } catch (SQLException exception) {
            logger.error("Got error while closing connection", exception);
        }
        return names;
    }

    public List<UUID> findAllIds() {
        List<UUID> uuids = new ArrayList<>();
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
                var statement = connection.prepareStatement(SELECT_ALL_SQL);
                var resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                var customerName = resultSet.getString("name");
                var customerId = toUUID(resultSet.getBytes("customer_id"));
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                uuids.add(customerId);
            }

        } catch (SQLException exception) {
            logger.error("Got error while closing connection", exception);
        }
        return uuids;
    }

    public int insertCustomer(UUID customerId, String name, String email) {
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
                var statement = connection.prepareStatement(INSERT_SQL);
        ) {
            statement.setBytes(1, customerId.toString().getBytes());
            statement.setString(2, name);
            statement.setString(3, email);
            return statement.executeUpdate();
        } catch (SQLException exception) {
            logger.error("Got error while closing connection", exception);
        }
        return 0;
    }

    public int updateCustomerName(UUID customerId, String name) {
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
                var statement = connection.prepareStatement(UPDATE_BY_ID_SQL);
        ) {
            statement.setString(1, name);
            statement.setBytes(2, customerId.toString().getBytes());
            return statement.executeUpdate();
        } catch (SQLException exception) {
            logger.error("Got error while closing connection", exception);
        }
        return 0;
    }

    public int deleteAllCustomers() {
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
                var statement = connection.prepareStatement(DELETE_ALL_SQL);
        ) {
            return statement.executeUpdate();
        } catch (SQLException exception) {
            logger.error("Got error while closing connection", exception);
        }
        return 0;
    }

    public void transactionTest(Customer customer) {
        String updateNameSql = "UPDATE customers SET name = ? WHERE customer_id = UUID_TO_BIN(?)";
        String updateEmailSql = "UPDATE customers SET email = ? WHERE customer_id = UUID_TO_BIN(?)";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!")
            connection.setAutoCommit(false);
            try (
                    var updateNameStatement = connection.prepareStatement(updateNameSql);
                    var updateEmailStatement = connection.prepareStatement(updateEmailSql);
            ) {
                updateNameStatement.setString(1, customer.getName());
                updateNameStatement.setBytes(2, customer.getCustomerId().toString().getBytes());
                updateNameStatement.executeUpdate();

                updateEmailStatement.setString(1, customer.getEmail());
                updateEmailStatement.setBytes(2, customer.getCustomerId().toString().getBytes());
                updateEmailStatement.executeUpdate();
                connection.setAutoCommit(true);
            }
        } catch (SQLException exception) {
            if (connection != null) {
                try {
                    connection.rollback();
                    connection.close();
                } catch (SQLException throwable) {
                    logger.error("Got error while closing connection", throwable);
                    throw new RuntimeException(exception);
                }
            }
            logger.error("Got error while closing connection", exception);
            throw new RuntimeException(exception);
        }
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
    public static void main(String[] args) {
        var customerRepository = new JdbcCustomerRepository();

//        customerRepository.transactionTest(
//                new Customer(UUID.fromString(
//                        "53ae4aa9-827d-4538-9afe-f2cf125b9d8a"),
//                        "updated-user",
//                        "test-user@gmail.com",
//                        LocalDateTime.now()
//                )
//        );
//        var count = customerRepository.deleteAllCustomers();
//        logger.info("deleted count -> {}", count);
//
        var customerId = UUID.randomUUID();
//        logger.info("created customerId -> {}", customerId);
//        customerRepository.insertCustomer(UUID.randomUUID(), "new-user", "new-user@gmail.com");
//        customerRepository.findAllIds().forEach(v -> logger.info("Found customerId : {}", v));
//        var customer2 = UUID.randomUUID();
        customerRepository.insertCustomer(customerId, "new-user2", "new-user2@gmail.com");
//        customerRepository.findAllName().forEach(v -> logger.info("Found name : {}", v));
//
//        customerRepository.updateCustomerName(customer2, "updated-user2");
    }
}
