package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.customer.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcCustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final String SELECT_SQL = "select * from customers WHERE name = ?";
    private final String SELECT_ALL_SQL = "select * from customers";
    private final String INSERT_SQL = "INSERT INTO customers(customer_id, name, email) VALUES (UUID_TO_BIN(?), ?, ?)";
    private final String DELETE_ALL_SQL = "DELETE FROM customers";
    private final String UPDATE_BY_ID_SQL = "UPDATE customers SET name = ? WHERE customer_id = UUID_TO_BIN(?)";

    public List<String> findNames(String name) {
        String SELECT_SQL = "select * from customers WHERE name = ?";
        List<String> names = new ArrayList<>();

        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "xogud");
                PreparedStatement statement = connection.prepareStatement(SELECT_SQL);
        ) {
            statement.setString(1, name);
            logger.info("statement -> {}", statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String customerName = resultSet.getString("name");
                    UUID customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("name"));
                    LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                    logger.info("customer id -> {}, name -> {}, createdAt -> {}", customerId, name, createdAt);
                    names.add(customerName);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            logger.error("Got error while closing connection", throwables);
        }
        return names;
    }

    public List<String> findAllNames() {
        List<String> names = new ArrayList<>();

        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "xogud");
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
        ) {
            logger.info("statement -> {}", statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String customerName = resultSet.getString("name");
                    UUID customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("name"));
                    LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                    logger.info("customer id -> {}, name -> {}, createdAt -> {}", customerId, customerName, createdAt);
                    names.add(customerName);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            logger.error("Got error while closing connection", throwables);
        }
        return names;
    }

    public List<UUID> findAllUuids() {
        List<UUID> uuids = new ArrayList<>();

        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "xogud");
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
        ) {
            logger.info("statement -> {}", statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String customerName = resultSet.getString("name");
                    final UUID customerId = toUUID(resultSet.getBytes("customer_id"));
                    LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                    logger.info("customer id -> {}, name -> {}, createdAt -> {}", customerId, customerName, createdAt);
                    uuids.add(customerId);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            logger.error("Got error while closing connection", throwables);
        }
        return uuids;
    }

    public int insertCustomer(UUID customerId, String name, String email) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "xogud");
                PreparedStatement statement = connection.prepareStatement(INSERT_SQL);
        ) {
            statement.setBytes(1, customerId.toString().getBytes());
            statement.setString(2, name);
            statement.setString(3, email);
            logger.info("statement -> {}", statement);
            return statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            logger.error("Got error while closing connection", throwables);
        }
        return 0;
    }

    public int updateCustomerName(UUID customerId, String name) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "xogud");
                PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID_SQL);
        ) {
            statement.setBytes(2, customerId.toString().getBytes());
            statement.setString(1, name);
            logger.info("statement -> {}", statement);
            return statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            logger.error("Got error while closing connection", throwables);
        }
        return 0;
    }

    public int deleteAllCustomers() {
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "xogud");
                PreparedStatement statement = connection.prepareStatement(DELETE_ALL_SQL);
        ) {
            return statement.executeUpdate();
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
        }
        return 0;
    }

    public void transactionTest(Customer customer) {
        String updateNameSql = "UPDATE customers SET name = ? WHERE customer_id = UUID_TO_BIN(?)";
        String updateEmailSql = "UPDATE customers SET email = ? WHERE customer_id = UUID_TO_BIN(?)";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "xogud");
            try (
                    PreparedStatement updateNameStatement = connection.prepareStatement(updateNameSql);
                    PreparedStatement updateEmailStatement = connection.prepareStatement(updateEmailSql);
            ) {
                connection.setAutoCommit(false);
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
                }
            }
            logger.error("Got error while closing connection", exception);
            throw new RuntimeException(exception);
        }
    }

    static UUID toUUID(byte[] bytes) {
        final ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static void main(String[] args) throws SQLException {
        final JdbcCustomerRepository customerRepository = new JdbcCustomerRepository();

        customerRepository.transactionTest(
                new Customer(UUID.fromString("58d3f282-4a0c-4bea-bb00-46f91f68e829"), "update-user", "new-user2@gmail.com", LocalDateTime.now()));


//        int count = customerRepository.deleteAllCustomers();
//        logger.info("deleted count -> {}", count);
//
//        final UUID customerId = UUID.randomUUID();
//        logger.info("created customerId -> {}", customerId);
//        customerRepository.insertCustomer(customerId, "new-user", "new-user@gmail.com");
//        customerRepository.findAllUuids().forEach(v -> logger.info("Found uuid : {}", v));
//        final UUID customer2 = UUID.randomUUID();
    }
}
