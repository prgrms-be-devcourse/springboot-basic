package org.prgrms.kdt;


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
    private static final String SELECT_BY_NAME_SQL = "SELECT * FROM customers WHERE name = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM customers";
    private static final String INSERT_SQL = "INSERT INTO customers (customer_id, name, email) VALUES (UUID_TO_BIN(?), ?, ?)";
    private static final String DELETE_ALL_SQL = "DELETE FROM customers";
    private static final String UPDATE_BY_ID_SQL = "UPDATE customers SET name = ? WHERE customer_id = ?";

    public List<String> findNames(String name) {

        List<String> names = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "admin");
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME_SQL)) {

            statement.setString(1, name);
            logger.info("statement -> {}", statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String customerName = resultSet.getString("name");
                    UUID customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                    LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                    logger.info("customerId -> {}, customer name -> {}, createdAt -> {}", customerId, customerName, createdAt);
                    names.add(customerName);
                }
            }
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
        }
        return names;
    }

    public List<String> findAllNames() {

        List<String> names = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "admin");
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String customerName = resultSet.getString("name");
                    UUID customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                    LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                    names.add(customerName);
                }
            }
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
        }
        return names;
    }

    public List<UUID> findAllIds() {

        List<UUID> uuids = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "admin");
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String customerName = resultSet.getString("name");
                    UUID customerId = toUUID(resultSet.getBytes("customer_id"));
                    LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                    uuids.add(customerId);
                }
            }
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
        }
        return uuids;
    }

    public int insertCustomer(UUID customerId, String name, String email) {

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "admin");
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {

            statement.setBytes(1, customerId.toString().getBytes());
            statement.setString(2, name);
            statement.setString(3, email);
            return statement.executeUpdate();
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
        }

        return 0;
    }

    public int updateCustomer(UUID customerId, String name) {

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "admin");
             PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID_SQL)) {

            statement.setString(1, name);
            statement.setBytes(2, customerId.toString().getBytes());
            return statement.executeUpdate();
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
        }

        return 0;
    }

    public int deleteAllCustomers() {

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "admin");
             PreparedStatement statement = connection.prepareStatement(DELETE_ALL_SQL)) {

            return statement.executeUpdate();
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
        }

        return 0;
    }

    static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
    public static void main(String[] args) {
        JdbcCustomerRepository customerRepository = new JdbcCustomerRepository();

        int count = customerRepository.deleteAllCustomers();
        logger.info("count -> {}", count);

        UUID customerId = UUID.randomUUID();
        logger.info("created customerId: {}", customerId);
        customerRepository.insertCustomer(customerId, "new-tester", "new-tester@gmail.com");

        customerRepository.findAllIds().forEach(v -> logger.info("Found customerId: {}", v));
    }

}
