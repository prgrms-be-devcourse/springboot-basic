package com.programmers.kdtspringorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *  강의 실습파일입니다.
 */
public class JdbcCustomerRepositoryTester {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepositoryTester.class);
    public final String SELECT_BY_NAME_SQL = "select * from customers WHERE name = ?";
    public final String SELECT_ALL_SQL = "select * from customers";
    public final String INSERT_SQL = "INSERT INTO customers(customer_id, name, email) VALUES(UUID_TO_BIN(?), ?, ?)";
    public final String UPDATE_BY_ID_SQL = "UPDATE customers SET name = ? WHERE customer_id = UUID_TO_BIN(?)";
    public final String DELETE_ALL_SQL = "DELETE FROM customers";

    public List<String> findNames(String name) {
        List<String> names = new ArrayList<>();
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "user", "user");
                var preparedStatement = connection.prepareStatement(SELECT_BY_NAME_SQL);
        ) {
            preparedStatement.setString(1, name);
            logger.info("preparedStatement -> {} ", preparedStatement);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String customerName = resultSet.getString("name");
                    UUID customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                    var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                    logger.info("customer name -> {}, id -> {}, creaedAt -> {}", customerName, customerId, createdAt);
                    names.add(customerName);
                }
            }
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        }

        return names;
    }

    public List<String> findAllName() {
        List<String> names = new ArrayList<>();
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "user", "user");
                var preparedStatement = connection.prepareStatement(SELECT_ALL_SQL);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            logger.info("preparedStatement -> {} ", preparedStatement);
            while (resultSet.next()) {
                String customerName = resultSet.getString("name");
                UUID customerId = toUUID(resultSet.getBytes("customer_id"));
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                logger.info("customer name -> {}, id -> {}, creaedAt -> {}", customerName, customerId, createdAt);
                names.add(customerName);
            }
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        }

        return names;
    }

    public List<UUID> findAllIds() {
        List<UUID> ids = new ArrayList<>();
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "user", "user");
                var preparedStatement = connection.prepareStatement(SELECT_ALL_SQL);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            logger.info("preparedStatement -> {} ", preparedStatement);
            while (resultSet.next()) {
                var customerName = resultSet.getString("name");
                UUID customerId = toUUID(resultSet.getBytes("customer_id"));
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                ids.add(customerId);
            }
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        }

        return ids;
    }

    public int insertCustomer(UUID customerId, String name, String email) {
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "user", "user");
                var preparedStatement = connection.prepareStatement(INSERT_SQL);
        ) {
            preparedStatement.setBytes(1, customerId.toString().getBytes());
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
            logger.info("preparedStatement -> {} ", preparedStatement);
            return preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        }
        return 0;
    }

    public int updateCustomerName(UUID customerId, String name) {
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "user", "user");
                var preparedStatement = connection.prepareStatement(UPDATE_BY_ID_SQL);
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setBytes(2, customerId.toString().getBytes());
            logger.info("preparedStatement -> {} ", preparedStatement);
            return preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        }
        return 0;
    }

    public int deleteAllCustomers() {
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "user", "user");
                var preparedStatement = connection.prepareStatement(DELETE_ALL_SQL);
        ) {
            logger.info("preparedStatement -> {} ", preparedStatement);
            return preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        }
        return 0;
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static void main(String[] args) {
        final JdbcCustomerRepositoryTester jdbcCustomerRepositoryTester = new JdbcCustomerRepositoryTester();
//        jdbcCustomerRepository.insertCustomer(UUID.randomUUID(), "new_user", "new-user@gmail.com");

        final int i = jdbcCustomerRepositoryTester.deleteAllCustomers();
        final UUID customer2 = UUID.randomUUID();
        logger.info("UUID : {}", customer2);
        jdbcCustomerRepositoryTester.insertCustomer(customer2, "customer2", "customer2@gmail.com");
        jdbcCustomerRepositoryTester.updateCustomerName(customer2, "cucucu");

        List<String> names = jdbcCustomerRepositoryTester.findAllName();
        names.forEach(v -> logger.info("Found name : {} ", v));
    }
}
